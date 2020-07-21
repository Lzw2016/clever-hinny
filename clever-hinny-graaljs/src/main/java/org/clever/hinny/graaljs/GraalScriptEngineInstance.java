package org.clever.hinny.graaljs;

import org.clever.hinny.api.AbstractScriptEngineInstance;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:58 <br/>
 */
public class GraalScriptEngineInstance extends AbstractScriptEngineInstance<Context, Value> {

    public GraalScriptEngineInstance(ScriptEngineContext<Context, Value> context) {
        super(context);
        Value engineBindings = this.context.getEngine().getBindings(GraalConstant.Js_Language_Id);
        Map<String, Object> contextMap = this.context.getContextMap();
        if (contextMap != null) {
            contextMap.forEach(engineBindings::putMember);
        }
        engineBindings.putMember(GlobalConstant.Engine_Require, this.context.getRequire());
        engineBindings.putMember(GlobalConstant.Engine_Global, this.context.getGlobal());
    }

    @Override
    public String getEngineName() {
        return context.getEngine().getEngine().getImplementationName();
    }

    @Override
    public String getEngineVersion() {
        return context.getEngine().getEngine().getVersion();
    }

    @Override
    public String getLanguageVersion() {
        // TODO getLanguageVersion
        // return context.getEngine().getEngine().
        return "??";
    }

    @Override
    protected ScriptObject<Value> newScriptObject(Value scriptObject) {
        return new GraalScriptObject(scriptObject);
    }
}
