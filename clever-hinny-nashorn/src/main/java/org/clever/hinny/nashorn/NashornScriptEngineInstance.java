package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.Getter;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.require.RequireInstance;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

import javax.script.Bindings;
import javax.script.ScriptContext;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:25 <br/>
 */
public class NashornScriptEngineInstance implements ScriptEngineInstance<ScriptObjectMirror> {
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
    private final ModuleCache<ScriptObjectMirror> moduleCache;
    /**
     * 引擎上下文
     */
    private final ScriptEngineContext<ScriptObjectMirror> context;
    /**
     * require加载其他模块
     */
    private final Require<ScriptObjectMirror> require;
    /**
     * 全局的require实例
     */
    private final RequireInstance requireInstance;
    /**
     * 共享的全局变量
     */
    private final ScriptObjectMirror global;

    private NashornScriptEngineInstance(
            Folder rootPath,
            ModuleCache<ScriptObjectMirror> moduleCache,
            ScriptEngineContext<ScriptObjectMirror> context,
            Require<ScriptObjectMirror> require,
            RequireInstance requireInstance,
            ScriptObjectMirror global) {
        this.engine = ScriptEngineUtils.creatEngine();
        this.rootPath = rootPath;
        this.moduleCache = moduleCache;
        this.require = require;
        this.context = context;
        this.global = global;
        this.requireInstance = requireInstance;


        Bindings engineBindings = this.engine.getBindings(ScriptContext.ENGINE_SCOPE);
        Map<String, Object> contextMap = this.context.getContextMap();
        if (contextMap != null) {
            engineBindings.putAll(contextMap);
        }
        // TODO: Module
        engineBindings.put(GlobalConstant.Engine_Module, "");
        engineBindings.put(GlobalConstant.Engine_Exports, "");
        engineBindings.put(GlobalConstant.Engine_Require, this.require);
        engineBindings.put(GlobalConstant.Engine_Global, this.global);
    }

    @Override
    public ScriptEngineContext<ScriptObjectMirror> getScriptEngineContext() {
        return null;
    }

    @Override
    public Folder getRootPath() {
        return null;
    }

    @Override
    public ScriptObjectMirror getGlobal() {
        return null;
    }
}