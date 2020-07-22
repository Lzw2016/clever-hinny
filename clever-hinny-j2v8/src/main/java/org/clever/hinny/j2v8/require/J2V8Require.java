package org.clever.hinny.j2v8.require;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Value;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.Module;
import org.clever.hinny.api.require.AbstractRequire;
import org.clever.hinny.api.require.Require;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/22 12:41 <br/>
 */
public class J2V8Require extends AbstractRequire<V8, V8Value> {

    public J2V8Require(ScriptEngineContext<V8, V8Value> context, Module<V8Value> currentModule, Folder currentModuleFolder) {
        super(context, currentModule, currentModuleFolder);
    }

    protected J2V8Require(ScriptEngineContext<V8, V8Value> context, Folder currentModuleFolder) {
        super(context, currentModuleFolder);
    }

    @Override
    protected V8Value newScriptObject() {
        return null;
    }

    @Override
    protected AbstractRequire<V8, V8Value> newRequire(ScriptEngineContext<V8, V8Value> context, Folder currentModuleFolder) {
        return null;
    }

    @Override
    protected Module<V8Value> newModule(ScriptEngineContext<V8, V8Value> context, String id, String filename, V8Value exports, Module<V8Value> parent, Require<V8Value> require) {
        return null;
    }

    @Override
    protected void moduleFunctionCall(V8Value function, V8Value that, V8Value exports, Require<V8Value> require, V8Value module, String filename, String dirname) {

    }
}
