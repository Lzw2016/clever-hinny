package org.clever.hinny.nashorn.require;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.NashornScriptObject;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:40 <br/>
 */
public class NashornRequire implements Require<NashornScriptObject> {
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
    public NashornScriptObject require(String path) throws Exception {
        return null;
    }
}
