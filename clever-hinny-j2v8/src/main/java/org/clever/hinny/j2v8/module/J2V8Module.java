package org.clever.hinny.j2v8.module;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Value;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.module.AbstractModule;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.Require;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/22 12:41 <br/>
 */
public class J2V8Module extends AbstractModule<V8, V8Value> {

    public J2V8Module(ScriptEngineContext<V8, V8Value> context, String id, String filename, V8Value exports, Module<V8Value> parent, Require<V8Value> require) {
        super(context, id, filename, exports, parent, require);
    }

    protected J2V8Module(ScriptEngineContext<V8, V8Value> context) {
        super(context);
    }

    @Override
    protected void initModule() {

    }

    @Override
    protected V8Value newScriptObject() {
        return null;
    }

    @Override
    protected void doTriggerOnLoaded() {

    }

    @Override
    protected void doTriggerOnRemove() {

    }

    @Override
    public ScriptObject<V8Value> getExportsWrapper() {
        return null;
    }
}
