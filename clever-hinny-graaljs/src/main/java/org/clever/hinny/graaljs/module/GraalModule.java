package org.clever.hinny.graaljs.module;

import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.module.AbstractModule;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.Require;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:58 <br/>
 */
public class GraalModule extends AbstractModule<Engine, Value> {

    public GraalModule(
            ScriptEngineContext<Engine, Value> context,
            String id,
            String filename,
            Value exports,
            Module<Value> parent,
            Require<Value> require) {
        super(context, id, filename, exports, parent, require);
    }

    @Override
    protected void initModule() {

    }

    @Override
    protected Value newScriptObject() {
        return null;
    }

    @Override
    protected void doTriggerOnLoaded() {

    }

    @Override
    protected void doTriggerOnRemove() {

    }

    @Override
    public ScriptObject<Value> getExportsWrapper() {
        return null;
    }
}
