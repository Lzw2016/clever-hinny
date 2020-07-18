package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.require.Require;

import javax.script.Bindings;
import javax.script.ScriptContext;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:25 <br/>
 */
public class NashornScriptEngineInstance implements ScriptEngineInstance<NashornScriptEngine, ScriptObjectMirror> {
    /**
     * 引擎上下文
     */
    private final ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context;

    public NashornScriptEngineInstance(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context) {
        // TODO 参数校验
        this.context = context;
        Bindings engineBindings = this.context.getEngine().getBindings(ScriptContext.ENGINE_SCOPE);
        Map<String, Object> contextMap = this.context.getContextMap();
        if (contextMap != null) {
            engineBindings.putAll(contextMap);
        }
        engineBindings.put(GlobalConstant.Engine_Require, this.context.getRequire());
        engineBindings.put(GlobalConstant.Engine_Global, this.context.getGlobal());
    }

    @Override
    public ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> getContext() {
        return context;
    }

    @Override
    public Folder getRootPath() {
        return context.getRootPath();
    }

    @Override
    public ScriptObjectMirror getGlobal() {
        return context.getGlobal();
    }

    @Override
    public Require<ScriptObjectMirror> getRequire() {
        return context.getRequire();
    }
}