package org.clever.hinny.nashorn.require;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.*;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:40 <br/>
 */
@Slf4j
public class NashornRequire implements Require<ScriptObjectMirror> {
    private static final String JS_File = ".js";
    private static final String JSON_File = ".json";
    /**
     * 文件可省略的后缀名
     */
    private static final String[] File_Suffix = new String[]{"", JS_File, JSON_File};

    /**
     * 当前线程缓存(fullPath -> script引擎对象)
     */
    private final ThreadLocal<Map<String, ScriptObjectMirror>> refCache = new ThreadLocal<>();
    /**
     * 编译脚本成ScriptModule
     */
    private final CompileModule<ScriptObjectMirror> compileModule;
    /**
     * 当前模块缓存
     */
    private final ModuleCache<ScriptObjectMirror> moduleCache;
    /**
     * 当前模块所属文件夹
     */
    private final Folder currentModuleFolder;

    public NashornRequire(CompileModule<ScriptObjectMirror> compileModule, ModuleCache<ScriptObjectMirror> moduleCache, Folder currentModuleFolder) {
        this.compileModule = compileModule;
        this.moduleCache = moduleCache;
        this.currentModuleFolder = currentModuleFolder;
    }

    @Override
    public ScriptObjectMirror require(final String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("模块id不能为空");
        }
        Folder moduleFile = loadModule(id);
        if (moduleFile == null || !moduleFile.isFile()) {
            throw new ModuleNotFoundException("找不到模块id=" + id);
        }
        // 确保每个线程都有自己的refCache
        boolean needRemove = false;
        if (refCache.get() == null) {
            needRemove = true;
            refCache.set(new HashMap<>());
        }
        String fullPath = moduleFile.getFullPath();
        ScriptObjectMirror cached = refCache.get().get(fullPath);
        if (cached != null) {
            log.debug("# RefCache 命中缓存 -> {}", fullPath);
            return cached;
        } else {
            // 存储对当前加载模块的引用，以避免循环require
            log.debug("# RefCache 加入缓存 -> {}", fullPath);
            refCache.get().put(fullPath, ScriptEngineUtils.newObject());
        }
        // 加载 Module
        try {
            ModuleInstance<ScriptObjectMirror> moduleInstance = loadOrCompileModule(moduleFile);
            return moduleInstance.getExports();
        } catch (Exception e) {
            throw new LoadModuleException("加载ScriptModule失败，id=" + id, e);
        } finally {
            // 需要删除refCache防止内存泄漏
            if (needRemove && refCache.get() != null) {
                refCache.remove();
            }
        }
    }

    /**
     * 加载或者编译模块
     */
    protected ModuleInstance<ScriptObjectMirror> loadOrCompileModule(Folder moduleFile) throws Exception {
        ScriptObjectMirror scriptObjectMirror;
        String name = moduleFile.getName();
        if (name.endsWith(JS_File)) {
            scriptObjectMirror = compileModule.compileJavaScriptModule(moduleFile);
        } else if (name.endsWith(JSON_File)) {
            scriptObjectMirror = compileModule.compileJsonModule(moduleFile);
        } else {
            throw new UnSupportModuleException("不支持的ScriptModule类型，name=" + name);
        }


        return null;
    }

    /**
     * 根据加载模块路径，返回加载模块内容(解析模块步骤实现)
     */
    protected Folder loadModule(final String path) {
        // 判断path是相对路径还是绝对路径
        if (path.startsWith(Folder.Root_Path)
                || path.startsWith(Folder.Current_Path)
                || path.startsWith(Folder.Parent_Path)
                || path.startsWith("\\")
                || path.startsWith(".\\")
                || path.startsWith("..\\")) {
            // 相对路径
            return resolvedModulePath(currentModuleFolder, path);
        }
        // 绝对路径
        Folder nodeModules = currentModuleFolder.concat(GlobalConstant.CommonJS_Node_Modules);
        while (nodeModules != null && nodeModules.isDir()) {
            Folder modulePath = resolvedModulePath(nodeModules, path);
            if (modulePath != null) {
                return modulePath;
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
    protected Folder resolvedModulePath(final Folder dir, final String modulePath) {
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
