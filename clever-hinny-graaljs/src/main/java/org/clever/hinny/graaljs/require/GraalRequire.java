package org.clever.hinny.graaljs.require;

import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.AbstractRequire;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.graaljs.module.GraalModule;
import org.clever.hinny.graaljs.utils.ScriptEngineUtils;
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

    public GraalRequire(ScriptEngineContext<Context, Value> context, Folder currentModuleFolder) {
        super(context, currentModuleFolder);
    }


    @Override
    protected Value newScriptObject() {
        return ScriptEngineUtils.newObject(context.getEngine());
    }

    @Override
    protected AbstractRequire<Context, Value> newRequire(
            ScriptEngineContext<Context, Value> context,
            Folder currentModuleFolder) {
        return new GraalRequire(context, currentModuleFolder);
    }

    @Override
    protected Module<Value> newModule(
            ScriptEngineContext<Context, Value> context,
            String id,
            String filename,
            Value exports,
            Module<Value> parent,
            Require<Value> require) {
        return new GraalModule(context, id, filename, exports, parent, require);
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
        Assert.isTrue(function.canExecute(), "参数function必须是一个可执行函数ScriptObject");
        Context engine = context.getEngine();
        try {
            engine.enter();
            function.executeVoid(exports, require, module, filename, dirname);
        } finally {
            if (engine != null) {
                engine.leave();
            }
        }
    }
}
