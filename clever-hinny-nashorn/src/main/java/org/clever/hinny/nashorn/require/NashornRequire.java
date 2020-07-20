package org.clever.hinny.nashorn.require;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.AbstractRequire;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.module.NashornModule;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:40 <br/>
 */
@Slf4j
public class NashornRequire extends AbstractRequire<NashornScriptEngine, ScriptObjectMirror> {

    public NashornRequire(
            ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context,
            Module<ScriptObjectMirror> currentModule,
            Folder currentModuleFolder) {
        super(context, currentModule, currentModuleFolder);
    }

    public NashornRequire(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context, Folder currentModuleFolder) {
        super(context, currentModuleFolder);
    }

    @Override
    protected ScriptObjectMirror newScriptObject() {
        return ScriptEngineUtils.newObject();
    }

    @Override
    protected AbstractRequire<NashornScriptEngine, ScriptObjectMirror> newRequire(
            ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context,
            Folder currentModuleFolder) {
        return new NashornRequire(context, currentModuleFolder);
    }

    @Override
    protected Module<ScriptObjectMirror> newModule(
            ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context,
            String id,
            String filename,
            ScriptObjectMirror exports,
            Module<ScriptObjectMirror> parent,
            Require<ScriptObjectMirror> require) {
        return new NashornModule(context, id, filename, exports, parent, require);
    }

    @Override
    protected void moduleFunctionCall(
            ScriptObjectMirror function,
            ScriptObjectMirror that,
            ScriptObjectMirror exports,
            Require<ScriptObjectMirror> require,
            ScriptObjectMirror module,
            String filename,
            String dirname) {
        function.call(that, exports, require, module, filename, dirname);
    }
}
