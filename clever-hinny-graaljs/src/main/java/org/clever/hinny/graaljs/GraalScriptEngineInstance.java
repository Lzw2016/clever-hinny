package org.clever.hinny.graaljs;

import org.clever.hinny.api.AbstractScriptEngineInstance;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:58 <br/>
 */
public class GraalScriptEngineInstance extends AbstractScriptEngineInstance<Context, Value> {

    public GraalScriptEngineInstance(ScriptEngineContext<Context, Value> context) {
        super(context);
    }

    @Override
    protected ScriptObject<Value> newScriptObject(Value scriptObject) {
        return null;
    }

    @Override
    public String getEngineName() {
        return null;
    }

    @Override
    public String getEngineVersion() {
        return null;
    }

    @Override
    public String getLanguageVersion() {
        return null;
    }
}
