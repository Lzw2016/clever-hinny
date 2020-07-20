package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.AbstractScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.MemoryModuleCache;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.module.NashornCompileModule;
import org.clever.hinny.nashorn.module.NashornModule;
import org.clever.hinny.nashorn.require.NashornRequire;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

import java.util.Collections;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:53 <br/>
 */
public class NashornScriptEngineContext extends AbstractScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> {

    public NashornScriptEngineContext() {
        super();
    }

    public NashornScriptEngineContext(
            NashornScriptEngine engine,
            Map<String, Object> contextMap,
            Folder rootPath,
            ModuleCache<ScriptObjectMirror> moduleCache,
            Require<ScriptObjectMirror> require,
            CompileModule<ScriptObjectMirror> compileModule,
            ScriptObjectMirror global) {
        super(engine, contextMap, rootPath, moduleCache, require, compileModule, global);
    }

    public static class Builder extends AbstractBuilder<NashornScriptEngine, ScriptObjectMirror> {
        public Builder(Folder rootPath) {
            super(rootPath);
        }

        public static Builder create(Folder rootPath) {
            return new Builder(rootPath);
        }

        /**
         * 创建 ScriptEngineContext
         */
        public NashornScriptEngineContext build() {
            NashornScriptEngineContext context = new NashornScriptEngineContext();
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
                NashornModule mainModule = NashornModule.createMainModule(context);
                require = new NashornRequire(context, mainModule, rootPath);
            }
            if (compileModule == null) {
                compileModule = new NashornCompileModule(context);
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
