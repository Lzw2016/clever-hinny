package org.clever.hinny.j2v8;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Value;
import org.clever.hinny.api.AbstractScriptEngineInstance;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/22 12:40 <br/>
 */
public class J2V8ScriptEngineInstance extends AbstractScriptEngineInstance<V8, V8Value> {
    
    public J2V8ScriptEngineInstance(ScriptEngineContext<V8, V8Value> context) {
        super(context);
    }

    @Override
    protected ScriptObject<V8Value> newScriptObject(V8Value scriptObject) {
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
