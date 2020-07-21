package org.clever.hinny.graaljs;

import org.clever.hinny.api.AbstractScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.MemoryModuleCache;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.graaljs.module.GraalCompileModule;
import org.clever.hinny.graaljs.module.GraalModule;
import org.clever.hinny.graaljs.require.GraalRequire;
import org.clever.hinny.graaljs.utils.ScriptEngineUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.Collections;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:58 <br/>
 */
public class GraalScriptEngineContext extends AbstractScriptEngineContext<Context, Value> {
    public GraalScriptEngineContext() {
        super();
    }

    public GraalScriptEngineContext(
            Context engine,
            Map<String, Object> contextMap,
            Folder rootPath,
            ModuleCache<Value> moduleCache,
            Require<Value> require,
            CompileModule<Value> compileModule,
            Value global) {
        super(engine, contextMap, rootPath, moduleCache, require, compileModule, global);
    }

    public static class Builder extends AbstractBuilder<Context, Value> {
        public Builder(Folder rootPath) {
            super(rootPath);
        }

        public static Builder create(Folder rootPath) {
            return new Builder(rootPath);
        }

        /**
         * 创建 ScriptEngineContext
         */
        public GraalScriptEngineContext build() {
            GraalScriptEngineContext context = new GraalScriptEngineContext();
            if (engine == null) {
                engine = ScriptEngineUtils.creatEngine();
            }
            if (contextMap == null) {
                contextMap = Collections.emptyMap();
            }
            if (moduleCache == null) {
                moduleCache = new MemoryModuleCache<>();
            }
            if (require == null) {
                GraalModule mainModule = GraalModule.createMainModule(context);
                require = new GraalRequire(context, mainModule, rootPath);
            }
            if (compileModule == null) {
                compileModule = new GraalCompileModule(context);
            }
            if (global == null) {
                global = ScriptEngineUtils.newObject();
            }
            context.engine = engine;
            context.contextMap = contextMap;
            context.moduleCache = moduleCache;
            context.require = require;
            context.compileModule = compileModule;
            context.global = global;
            return context;
        }
    }
}
