package org.clever.hinny.graaljs.require;

import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.AbstractRequire;
import org.clever.hinny.api.require.Require;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:58 <br/>
 */
public class GraalRequire extends AbstractRequire<Context, Value> {

    public GraalRequire(
            ScriptEngineContext<Context, Value> context,
            Module<Value> currentModule,
            Folder currentModuleFolder) {
        super(context, currentModule, currentModuleFolder);
    }

    @Override
    protected Value newScriptObject() {
        return null;
    }

    @Override
    protected AbstractRequire<Context, Value> newRequire(
            ScriptEngineContext<Context, Value> context,
            Folder currentModuleFolder) {
        return null;
    }

    @Override
    protected Module<Value> newModule(
            ScriptEngineContext<Context, Value> context,
            String id,
            String filename,
            Value exports,
            Module<Value> parent,
            Require<Value> require) {
        return null;
    }

    @Override
    protected void moduleFunctionCall(
            Value function,
            Value that,
            Value exports,
            Require<Value> require,
            Value module,
            String filename,
            String dirname) {

    }
}
