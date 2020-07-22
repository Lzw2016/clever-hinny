package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.AbstractScriptEngineInstance;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.folder.Folder;

import javax.script.Bindings;
import javax.script.ScriptContext;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:25 <br/>
 */
public class NashornScriptEngineInstance extends AbstractScriptEngineInstance<NashornScriptEngine, ScriptObjectMirror> {

    public NashornScriptEngineInstance(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context) {
        super(context);
        Bindings engineBindings = this.context.getEngine().getBindings(ScriptContext.ENGINE_SCOPE);
        Map<String, Object> contextMap = this.context.getContextMap();
        if (contextMap != null) {
            engineBindings.putAll(contextMap);
        }
        engineBindings.put(GlobalConstant.Engine_Require, this.context.getRequire());
        engineBindings.put(GlobalConstant.Engine_Global, this.context.getGlobal());
    }

    @Override
    public String getEngineName() {
        return context.getEngine().getFactory().getEngineName();
    }

    @Override
    public String getEngineVersion() {
        return context.getEngine().getFactory().getEngineVersion();
    }

    @Override
    public String getLanguageVersion() {
        return context.getEngine().getFactory().getLanguageVersion();
    }

    @Override
    protected ScriptObject<ScriptObjectMirror> newScriptObject(ScriptObjectMirror scriptObject) {
        return new NashornScriptObject(context, scriptObject);
    }

    public static class Builder extends AbstractBuilder<NashornScriptEngine, ScriptObjectMirror> {
        /**
         * @param rootPath 根路径文件夹
         */
        public Builder(Folder rootPath) {
            super(rootPath);
        }

        public static Builder create(Folder rootPath) {
            return new Builder(rootPath);
        }

        /**
         * 创建 ScriptEngineContext
         */
        public NashornScriptEngineInstance build() {
            ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context = NashornScriptEngineContext.Builder.create(rootPath)
                    .setEngine(engine)
                    .setContextMap(contextMap)
                    .setModuleCache(moduleCache)
                    .setRequire(require)
                    .setCompileModule(compileModule)
                    .setGlobal(global)
                    .build();
            return new NashornScriptEngineInstance(context);
        }
    }
}