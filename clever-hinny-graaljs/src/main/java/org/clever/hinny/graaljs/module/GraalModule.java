package org.clever.hinny.graaljs.module;

import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.module.AbstractModule;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.graaljs.GraalScriptObject;
import org.clever.hinny.graaljs.utils.ScriptEngineUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:58 <br/>
 */
public class GraalModule extends AbstractModule<Context, Value> {

    public GraalModule(
            ScriptEngineContext<Context, Value> context,
            String id,
            String filename,
            Value exports,
            Module<Value> parent,
            Require<Value> require) {
        super(context, id, filename, exports, parent, require);
    }

    private GraalModule(ScriptEngineContext<Context, Value> context) {
        super(context);
    }

    /**
     * 创建主模块(根模块)
     */
    public static GraalModule createMainModule(ScriptEngineContext<Context, Value> context) {
        return new GraalModule(context);
    }

    @Override
    protected void initModule() {
        this.module.putMember(GlobalConstant.Module_Id, this.id);
        this.module.putMember(GlobalConstant.Module_Filename, this.filename);
        this.module.putMember(GlobalConstant.Module_Loaded, this.loaded);
        if (this.parent != null) {
            this.module.putMember(GlobalConstant.Module_Parent, this.parent.getModule());
        }
        // TODO  Module_Paths
        this.module.putMember(GlobalConstant.Module_Paths, ScriptEngineUtils.newArray());
        // TODO  Module_Children
        this.module.putMember(GlobalConstant.Module_Children, ScriptEngineUtils.newArray());
        this.module.putMember(GlobalConstant.Module_Exports, this.exports);
        this.module.putMember(GlobalConstant.Module_Require, this.require);
    }

    @Override
    protected Value newScriptObject() {
        return ScriptEngineUtils.newObject();
    }

    @Override
    public ScriptObject<Value> getExportsWrapper() {
        return new GraalScriptObject(exports);
    }

    @Override
    protected void doTriggerOnLoaded() {
        // TODO triggerOnLoaded
    }

    @Override
    protected void doTriggerOnRemove() {
        // TODO triggerOnRemove
    }
}
