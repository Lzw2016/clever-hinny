package org.clever.hinny.nashorn.module;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/18 09:20 <br/>
 */
public class NashornCompileModule implements CompileModule<ScriptObjectMirror> {
    /**
     * NashornScriptEngine
     */
    private final NashornScriptEngine engine;

    public NashornCompileModule(NashornScriptEngine engine) {
        this.engine = engine;
    }

    @Override
    public ScriptObjectMirror compileJsonModule(Folder path) throws Exception {

        return null;
    }

    @Override
    public ScriptObjectMirror compileJavaScriptModule(Folder path) throws Exception {
        return null;
    }
}
