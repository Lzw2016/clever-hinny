package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:53 <br/>
 */
public class NashornScriptEngineContext implements ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> {
    /**
     * NashornScriptEngine
     */
    private final NashornScriptEngine engine;
    /**
     * 自定义引擎全局对象
     */
    private final Map<String, Object> contextMap = new ConcurrentHashMap<>();
    /**
     * 根路径文件夹
     */
    private final Folder rootPath;
    /**
     * 模块缓存
     */
    private final ModuleCache<ScriptObjectMirror> moduleCache;
    /**
     * 全局require实例(根目录require)
     */
    private final Require<ScriptObjectMirror> require;
    /**
     * 编译脚本成ScriptModule
     */
    private final CompileModule<ScriptObjectMirror> compileModule;
    /**
     * 引擎全局变量
     */
    private final ScriptObjectMirror global;

    public NashornScriptEngineContext(
            NashornScriptEngine engine,
            Map<String, Object> contextMap,
            Folder rootPath,
            ModuleCache<ScriptObjectMirror> moduleCache,
            Require<ScriptObjectMirror> require,
            CompileModule<ScriptObjectMirror> compileModule,
            ScriptObjectMirror global) {
        // TODO 参数校验
        this.engine = engine;
        if (contextMap != null) {
            this.contextMap.putAll(contextMap);
        }
        this.rootPath = rootPath;
        this.moduleCache = moduleCache;
        this.require = require;
        this.compileModule = compileModule;
        this.global = global;
    }

    @Override
    public NashornScriptEngine getEngine() {
        return engine;
    }

    @Override
    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    @Override
    public Folder getRootPath() {
        return rootPath;
    }

    @Override
    public ModuleCache<ScriptObjectMirror> getModuleCache() {
        return moduleCache;
    }

    @Override
    public Require<ScriptObjectMirror> getRequire() {
        return require;
    }

    @Override
    public CompileModule<ScriptObjectMirror> getCompileModule() {
        return compileModule;
    }

    @Override
    public ScriptObjectMirror getGlobal() {
        return global;
    }
}
