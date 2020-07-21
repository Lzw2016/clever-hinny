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
    protected void initModule() {
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
        this.module.put(GlobalConstant.Module_Exports, this.exports);
        this.module.put(GlobalConstant.Module_Require, this.require);
    }

    @Override
    protected ScriptObjectMirror newScriptObject() {
        return ScriptEngineUtils.newObject();
    }

    @Override
    public ScriptObject<ScriptObjectMirror> getExportsWrapper() {
        return new NashornScriptObject(exports);
    }

    @Override
    protected void doTriggerOnLoaded() {
        this.module.put(GlobalConstant.Module_Loaded, true);
        // 修正导出对象
        Object exportsObject = this.module.getMember(GlobalConstant.Module_Exports);
        if (!exports.equals(exportsObject)) {
            log.warn("模块的exports被直接赋值，filename={}", filename);
            if (exportsObject instanceof ScriptObjectMirror) {
                ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) exportsObject;
                ScriptObjectType type = ScriptEngineUtils.typeof(scriptObjectMirror);
                if (Objects.equals(type, ScriptObjectType.Object) || Objects.equals(type, ScriptObjectType.Array)) {
                    exports.putAll(scriptObjectMirror);
                } else {
                    exports = scriptObjectMirror;
                }
            } else if (exportsObject instanceof Number) {
                exports = ScriptEngineUtils.newNumber(exportsObject);
            } else if (exportsObject instanceof Boolean) {
                exports = ScriptEngineUtils.newBoolean(exportsObject);
            } else if (exportsObject instanceof String) {
                exports = ScriptEngineUtils.newString(exportsObject);
            } else {
                log.error("模块的exports被直接赋值，且是一个不支持的类型。filename={} | type={}", filename, exportsObject.getClass());
                exports = ScriptEngineUtils.newObject(exportsObject);
            }
        }
        // TODO triggerOnLoaded
    }

    @Override
    protected void doTriggerOnRemove() {
        // TODO triggerOnRemove
    }
}
