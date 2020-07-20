package org.clever.hinny.nashorn.require;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.module.*;
import org.clever.hinny.api.require.AbstractRequire;
import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.nashorn.module.NashornModule;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:40 <br/>
 */
@Slf4j
public class NashornRequire extends AbstractRequire<NashornScriptEngine, ScriptObjectMirror> {
    private static final String JS_File = ".js";
    private static final String JSON_File = ".json";
    /**
     * 文件可省略的后缀名
     */
    private static final String[] File_Suffix = new String[]{"", JS_File, JSON_File};
    /**
     * 当前线程缓存(fullPath -> script引擎对象)
     */
    private static final ThreadLocal<Map<String, ScriptObjectMirror>> Ref_Cache = new ThreadLocal<>();

    /**
     * 当前模块实例
     */
    private Module<ScriptObjectMirror> currentModule;
    /**
     * 当前模块所属文件夹
     */
    private final Folder currentModuleFolder;

    public NashornRequire(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context, Module<ScriptObjectMirror> currentModule, Folder currentModuleFolder) {
        super(context);
        Assert.notNull(currentModule, "参数currentModule不能为空");
        Assert.notNull(currentModuleFolder, "参数currentModuleFolder不能为空");
        this.currentModule = currentModule;
        this.currentModuleFolder = currentModuleFolder;
    }

    private NashornRequire(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context, Folder currentModuleFolder) {
        super(context);
        Assert.notNull(currentModuleFolder, "参数currentModuleFolder不能为空");
        this.currentModuleFolder = currentModuleFolder;
    }

    @Override
    public ScriptObjectMirror require(final String id) {
        Assert.isNotBlank(id, "参数id不能为空");
        Folder moduleFile = loadModuleFolder(id);
        if (moduleFile == null || !moduleFile.isFile()) {
            throw new ModuleNotFoundException("找不到模块id=" + id);
        }
        // 确保每个线程都有自己的refCache
        boolean needRemove = false;
        if (Ref_Cache.get() == null) {
            needRemove = true;
            Ref_Cache.set(new HashMap<>());
        }
        String fullPath = moduleFile.getFullPath();
        ScriptObjectMirror cached = Ref_Cache.get().get(fullPath);
        if (cached != null) {
            log.debug("# RefCache 命中缓存 -> {}", fullPath);
            return cached;
        } else {
            // 存储对当前加载模块的引用，以避免循环require
            log.debug("# RefCache 加入缓存 -> {}", fullPath);
            Ref_Cache.get().put(fullPath, ScriptEngineUtils.newObject());
        }
        // 加载 Module
        try {
            Module<ScriptObjectMirror> module = loadOrCompileModule(moduleFile);
            module.triggerOnLoaded();
            return module.getExports();
        } catch (Exception e) {
            throw new LoadModuleException("加载ScriptModule失败，id=" + id, e);
        } finally {
            // 需要删除refCache防止内存泄漏
            if (needRemove && Ref_Cache.get() != null) {
                Ref_Cache.remove();
            }
        }
    }

    /**
     * 加载或者编译模块
     */
    protected Module<ScriptObjectMirror> loadOrCompileModule(Folder moduleFile) throws Exception {
        ModuleCache<ScriptObjectMirror> moduleCache = context.getModuleCache();
        CompileModule<ScriptObjectMirror> compileModule = context.getCompileModule();
        final String fullPath = moduleFile.getFullPath();
        // 从缓存中加载模块
        Module<ScriptObjectMirror> module = moduleCache.get(fullPath);
        if (module != null) {
            log.debug("# ModuleCache 命中缓存 -> {}", fullPath);
            return module;
        }
        // 编译加载模块
        String name = moduleFile.getName();
        NashornRequire require = new NashornRequire(context, moduleFile.getParent());
        if (name.endsWith(JSON_File)) {
            // json模块
            ScriptObjectMirror exports = compileModule.compileJsonModule(moduleFile);
            module = new NashornModule(context, fullPath, fullPath, exports, currentModule, require);
            require.currentModule = module;
        } else if (name.endsWith(JS_File)) {
            // js模块
            ScriptObjectMirror exports = Ref_Cache.get() != null ? Ref_Cache.get().get(fullPath) : null;
            if (exports == null) {
                exports = ScriptEngineUtils.newObject();
            }
            ScriptObjectMirror function = compileModule.compileJavaScriptModule(moduleFile);
            module = new NashornModule(context, fullPath, fullPath, exports, currentModule, require);
            require.currentModule = module;
            // (function(exports, require, module, __filename, __dirname) {})
            // this         --> 当前模块
            // exports      --> created.exports
            // require      --> created (Module 对象实现了RequireFunction接口)
            // module       --> created.module
            // __filename   --> filename
            // __dirname    --> dirname
            function.call(module.getModule(), module.getExports(), module.getRequire(), module.getModule(), moduleFile.getFullPath(), moduleFile.getParent().getFullPath());
        } else {
            throw new UnSupportModuleException("不支持的ScriptModule类型，name=" + name);
        }
        // 缓存当前加载的 Module
        log.debug("# ModuleCache 加入缓存 -> {}", fullPath);
        moduleCache.put(fullPath, module);
        return module;
    }

    /**
     * 根据加载模块路径，返回加载模块内容(解析模块步骤实现)
     */
    protected Folder loadModuleFolder(final String path) {
        // 判断path是相对路径还是绝对路径
        if (path.startsWith(Folder.Root_Path)
                || path.startsWith(Folder.Current_Path)
                || path.startsWith(Folder.Parent_Path)
                || path.startsWith("\\")
                || path.startsWith(".\\")
                || path.startsWith("..\\")) {
            // 相对路径
            return resolvedModuleFolder(currentModuleFolder, path);
        }
        // 绝对路径
        Folder nodeModules = currentModuleFolder.concat(GlobalConstant.CommonJS_Node_Modules);
        while (nodeModules != null) {
            if (nodeModules.isDir()) {
                Folder modulePath = resolvedModuleFolder(nodeModules, path);
                if (modulePath != null) {
                    return modulePath;
                }
            }
            // 向上级目录查找
            nodeModules = nodeModules.concat(Folder.Parent_Path, Folder.Parent_Path, GlobalConstant.CommonJS_Node_Modules);
        }
        return null;
    }

    /**
     * 解析模块的核心逻辑，解析不到返回null
     * <pre>
     *  参数
     *    path:   .../some/path
     *    module: (module_path)
     *  解析过程
     *    .../some/path/(module_path)
     *    .../some/path/(module_path).js
     *    .../some/path/(module_path)/package.json 中的main属性
     *    .../some/path/(module_path)/index.js
     * </pre>
     *
     * @param dir        当前解析目录
     * @param modulePath 模块路径
     */
    protected Folder resolvedModuleFolder(final Folder dir, final String modulePath) {
        // 直接从文件加载
        for (String suffix : File_Suffix) {
            Folder moduleFile = dir.concat(modulePath + suffix);
            if (moduleFile != null && moduleFile.isFile()) {
                return moduleFile;
            }
        }
        // 从文件夹加载
        Folder moduleDir = dir.concat(modulePath);
        if (moduleDir != null && moduleDir.isDir()) {
            // 加载package.json中的main模块
            Folder packageJson = moduleDir.concat(GlobalConstant.CommonJS_Package);
            if (packageJson != null && packageJson.isFile()) {
                String packageJsonStr = packageJson.getFileContent();
                if (StringUtils.isNotBlank(packageJsonStr)) {
                    String main = null;
                    try {
                        JSONObject jsonObject = new JSONObject(packageJsonStr);
                        if (jsonObject.has(GlobalConstant.CommonJS_Package_Main)) {
                            main = jsonObject.getString(GlobalConstant.CommonJS_Package_Main);
                        }
                    } catch (Exception ignored) {
                    }
                    if (StringUtils.isNotBlank(main)) {
                        Folder mainFolder = moduleDir.concat(main);
                        if (mainFolder != null && mainFolder.isFile()) {
                            return mainFolder;
                        }
                    }
                }
            }
            // 加载默认的index.js模块
            Folder indexFolder = moduleDir.concat(GlobalConstant.CommonJS_Index);
            if (indexFolder != null && indexFolder.isFile()) {
                return indexFolder;
            }
        }
        return null;
    }
}
