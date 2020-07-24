package org.clever.hinny.nashorn.module;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.ScriptObjectType;
import org.clever.hinny.api.module.AbstractModule;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.NashornScriptObject;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/15 22:39 <br/>
 */
@Slf4j
public class NashornModule extends AbstractModule<NashornScriptEngine, ScriptObjectMirror> {
    public NashornModule(
            ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context,
            String id,
            String filename,
            ScriptObjectMirror exports,
            Module<ScriptObjectMirror> parent,
            Require<ScriptObjectMirror> require) {
        super(context, id, filename, exports, parent, require);
    }

    private NashornModule(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context) {
        super(context);
    }

    /**
     * 创建主模块(根模块)
     */
    public static NashornModule createMainModule(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context) {
        return new NashornModule(context);
    }

    @Override
    protected void initModule(ScriptObjectMirror exports) {
        this.module.put(GlobalConstant.Module_Id, this.id);
        this.module.put(GlobalConstant.Module_Filename, this.filename);
        this.module.put(GlobalConstant.Module_Loaded, this.loaded);
        if (this.parent != null) {
            this.module.put(GlobalConstant.Module_Parent, this.parent.getModule());
        }
        // TODO  Module_Paths
        this.module.put(GlobalConstant.Module_Paths, ScriptEngineUtils.newArray());
        // TODO  Module_Children
        this.module.put(GlobalConstant.Module_Children, ScriptEngineUtils.newArray());
        this.module.put(GlobalConstant.Module_Exports, exports);
        this.module.put(GlobalConstant.Module_Require, this.require);
    }

    @Override
    protected ScriptObjectMirror newScriptObject() {
        return ScriptEngineUtils.newObject();
    }

    @Override
    public ScriptObjectMirror getExports() {
        return (ScriptObjectMirror) this.module.get(GlobalConstant.Module_Exports);
    }

    @Override
    public ScriptObject<ScriptObjectMirror> getExportsWrapper() {
        return new NashornScriptObject(context, getExports());
    }

    @Override
    protected void doTriggerOnLoaded() {
        this.module.put(GlobalConstant.Module_Loaded, true);
        // TODO triggerOnLoaded
    }

    @Override
    protected void doTriggerOnRemove() {
        // TODO triggerOnRemove
    }
}
