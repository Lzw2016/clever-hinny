package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import lombok.Getter;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.require.RequireInstance;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:25 <br/>
 */
public class NashornScriptEngineInstance implements ScriptEngineInstance<NashornScriptObject> {
    /**
     * NashornScriptEngine
     */
    @Getter
    private final NashornScriptEngine engine;
    /**
     * 根路径文件夹
     */
    private final Folder rootPath;
    /**
     * 模块缓存
     */
    @Getter
    private final ModuleCache<NashornScriptObject> moduleCache;
    /**
     * 引擎上下文
     */
    private final ScriptEngineContext<NashornScriptObject> engineContext;
    /**
     * require加载其他模块
     */
    private final Require<NashornScriptObject> require;
    /**
     * 全局的require实例
     */
    private final RequireInstance requireInstance;
    /**
     * 共享的全局变量
     */
    private final NashornScriptObject global;

    public NashornScriptEngineInstance(
            Folder rootPath,
            ModuleCache<NashornScriptObject> moduleCache,
            ScriptEngineContext<NashornScriptObject> engineContext,
            Require<NashornScriptObject> require,
            RequireInstance requireInstance,
            NashornScriptObject global) {
        this.engine = ScriptEngineUtils.creatEngine();
        this.rootPath = rootPath;
        this.moduleCache = moduleCache;
        this.require = require;
        this.engineContext = engineContext;
        this.global = global;
        this.requireInstance = requireInstance;
    }

    @Override
    public ScriptEngineContext<NashornScriptObject> getScriptEngineContext() {
        return null;
    }

    @Override
    public Folder getRootPath() {
        return null;
    }

    @Override
    public NashornScriptObject getGlobal() {
        return null;
    }
}