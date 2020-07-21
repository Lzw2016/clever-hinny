package org.clever.hinny.graaljs.module;

import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.AbstractCompileModule;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:57 <br/>
 */
public class GraalCompileModule extends AbstractCompileModule<Context, Value> {

    public GraalCompileModule(ScriptEngineContext<Context, Value> context) {
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
