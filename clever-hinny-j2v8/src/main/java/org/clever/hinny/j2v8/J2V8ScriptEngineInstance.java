package org.clever.hinny.j2v8;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import org.clever.hinny.api.AbstractScriptEngineInstance;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.folder.Folder;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/22 12:40 <br/>
 */
public class J2V8ScriptEngineInstance extends AbstractScriptEngineInstance<V8, V8Object> {

    public J2V8ScriptEngineInstance(ScriptEngineContext<V8, V8Object> context) {
        super(context);
        V8 engineBindings = this.context.getEngine();
        Map<String, Object> contextMap = this.context.getContextMap();
        if (contextMap != null) {
            for (Map.Entry<String, Object> entry : contextMap.entrySet()) {
                // TODO registerJavaMethod
                // engineBindings.registerJavaMethod();
            }
        }
        // TODO registerJavaMethod
        // engineBindings.registerJavaMethod(GlobalConstant.Engine_Require, this.context.getRequire());
        engineBindings.add(GlobalConstant.Engine_Global, this.context.getGlobal());
    }

    @Override
    public String getEngineName() {
        return "J2V8";
    }

    @Override
    public String getEngineVersion() {
        return "V8 Version: " + V8.getV8Version();
    }

    @Override
    public String getLanguageVersion() {
        return "unknown";
    }

    @Override
    protected ScriptObject<V8Object> newScriptObject(V8Object scriptObject) {
        return new J2V8ScriptObject(context, scriptObject);
    }

    public static class Builder extends AbstractBuilder<V8, V8Object> {
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
        public J2V8ScriptEngineInstance build() {
            ScriptEngineContext<V8, V8Object> context = J2V8ScriptEngineContext.Builder.create(rootPath)
                    .setEngine(engine)
                    .setContextMap(contextMap)
                    .setModuleCache(moduleCache)
                    .setRequire(require)
                    .setCompileModule(compileModule)
                    .setGlobal(global)
                    .build();
            return new J2V8ScriptEngineInstance(context);
        }
    }
}
