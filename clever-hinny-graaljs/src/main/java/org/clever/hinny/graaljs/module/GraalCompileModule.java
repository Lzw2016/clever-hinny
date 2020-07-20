package org.clever.hinny.graaljs.module;

import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.AbstractCompileModule;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:57 <br/>
 */
public class GraalCompileModule extends AbstractCompileModule<Engine, Value> {

    public GraalCompileModule(ScriptEngineContext<Engine, Value> context) {
        super(context);
    }

    @Override
    public Value compileJsonModule(Folder path) throws Exception {
        return null;
    }

    @Override
    public Value compileJavaScriptModule(Folder path) throws Exception {
        return null;
    }
}
