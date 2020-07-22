package org.clever.hinny.j2v8;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Value;
import org.clever.hinny.api.AbstractScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/22 12:41 <br/>
 */
public class J2V8ScriptEngineContext extends AbstractScriptEngineContext<V8, V8Value> {

    public J2V8ScriptEngineContext() {
    }

    public J2V8ScriptEngineContext(V8 engine, Map<String, Object> contextMap, Folder rootPath, ModuleCache<V8Value> moduleCache, Require<V8Value> require, CompileModule<V8Value> compileModule, V8Value global) {
        super(engine, contextMap, rootPath, moduleCache, require, compileModule, global);
    }
}
