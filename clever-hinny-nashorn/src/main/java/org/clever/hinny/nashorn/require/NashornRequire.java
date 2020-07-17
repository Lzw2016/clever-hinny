package org.clever.hinny.nashorn.require;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.apache.commons.lang3.StringUtils;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.NashornScriptObject;
import org.json.JSONObject;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:40 <br/>
 */
public class NashornRequire implements Require<NashornScriptObject> {
    /**
     * 文件可省略的后缀名
     */
    private static final String[] File_Suffix = new String[]{"", ".js"};

    /**
     * 当前线程缓存(fullPath -> ScriptObject缓存)
     */
    private final ThreadLocal<Map<String, NashornScriptObject>> refCache = new ThreadLocal<>();
    /**
     * NashornScriptEngine
     */
    private final NashornScriptEngine engine;
    /**
     * 根路径文件夹
     */
    private final Folder rootPath;
    /**
     * 当前模块缓存
     */
    private final ModuleCache<NashornScriptObject> moduleCache;

    public NashornRequire(NashornScriptEngine engine, Folder rootPath, ModuleCache<NashornScriptObject> moduleCache) {
        this.engine = engine;
        this.rootPath = rootPath;
        this.moduleCache = moduleCache;
    }

    @Override
    public NashornScriptObject require(String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("模块id不能为空");
        }


        return null;
    }

    /**
     * 根据加载模块路径，返回加载模块内容(解析模块步骤实现)
     */
    protected void loadModule(String path) {
        // 判断path是相对路径还是绝对路径
        if (path.startsWith(Folder.Root_Path) || path.startsWith(Folder.Current_Path) || path.startsWith(Folder.Parent_Path)) {
            // 相对路径
            for (String suffix : File_Suffix) {
                Folder folder = rootPath.concat(path + suffix);
                if (folder == null) {
                    continue;
                }
                if (folder.isFile()) {
                    // TODO 直接加载模块文件
                    continue;
                }
                if (folder.isDir()) {
                    Folder packageJson = folder.concat(GlobalConstant.CommonJS_Package);
                    // 加载package.json中的main模块
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
                                // TODO 确定package.json中的main模块的根路径是否是整个文件系统的根路径
                                Folder mainFolder = folder.concat(main);
                                if (mainFolder != null && mainFolder.isFile()) {
                                    // TODO 加载main模块文件
                                    continue;
                                }
                            }
                        }
                    }
                    // 加载默认的index.js模块
                    Folder indexFolder = folder.concat(GlobalConstant.CommonJS_Index);
                    if (indexFolder != null && indexFolder.isFile()) {
                        // TODO 加载index.js文件
                    }
                }
            }
        }
        // if return;

        // 绝对路径

    }

    // Relative Absolute
    protected void relativePathLoadModule() {

    }

}
