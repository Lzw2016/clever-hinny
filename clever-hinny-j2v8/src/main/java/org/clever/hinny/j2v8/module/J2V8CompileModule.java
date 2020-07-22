package org.clever.hinny.j2v8.module;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Value;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.AbstractCompileModule;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/22 12:41 <br/>
 */
public class J2V8CompileModule extends AbstractCompileModule<V8, V8Value> {

    public J2V8CompileModule(ScriptEngineContext<V8, V8Value> context) {
        super(context);
    }

    @Override
    public V8Value compileJsonModule(Folder path) throws Exception {
        return null;
    }

    @Override
    public V8Value compileJavaScriptModule(Folder path) throws Exception {
        return null;
    }
}
