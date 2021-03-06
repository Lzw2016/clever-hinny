package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.AbstractScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.internal.LoggerConsole;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.MemoryModuleCache;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.internal.NashornLoggerFactory;
import org.clever.hinny.nashorn.internal.support.NashornObjectToString;
import org.clever.hinny.nashorn.module.NashornCompileModule;
import org.clever.hinny.nashorn.module.NashornModule;
import org.clever.hinny.nashorn.require.NashornRequire;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        private Set<Class<?>> denyAccessClass = new HashSet<>();

        public Builder(Folder rootPath) {
            super(rootPath);
            // 自定义 contextMap
            LoggerConsole.Instance.setObjectToString(NashornObjectToString.Instance);
            contextMap.put("console", LoggerConsole.Instance);
            contextMap.put("print", LoggerConsole.Instance);
            contextMap.put("LoggerFactory", NashornLoggerFactory.Instance);
        }

        public static Builder create(Folder rootPath) {
            return new Builder(rootPath);
        }

        /**
         * 增加JavaScript不允许访问的Class
         */
        public Builder addDenyAccessClass(Class<?> clazz) {
            if (denyAccessClass != null && clazz != null) {
                denyAccessClass.add(clazz);
            }
            return this;
        }

        /**
         * 设置JavaScript不允许访问的Class
         */
        public Builder setDenyAccessClass(Set<Class<?>> denyAccessClass) {
            this.denyAccessClass = denyAccessClass;
            return this;
        }

        /**
         * 创建 ScriptEngineContext
         */
        public NashornScriptEngineContext build() {
            NashornScriptEngineContext context = new NashornScriptEngineContext();
            // engine
            if (engine == null) {
                engine = ScriptEngineUtils.creatEngine(denyAccessClass);
            }
            context.engine = engine;
            // contextMap
            if (contextMap == null) {
                contextMap = Collections.emptyMap();
            }
            context.contextMap = contextMap;
            // rootPath
            context.rootPath = rootPath;
            // moduleCache
            if (moduleCache == null) {
                moduleCache = new MemoryModuleCache<>();
            }
            context.moduleCache = moduleCache;
            // require
            if (require == null) {
                NashornModule mainModule = NashornModule.createMainModule(context);
                require = new NashornRequire(context, mainModule, rootPath);
            }
            context.require = require;
            // compileModule
            if (compileModule == null) {
                compileModule = new NashornCompileModule(context);
            }
            context.compileModule = compileModule;
            // global
            if (global == null) {
                global = ScriptEngineUtils.newObject();
            }
            context.global = global;
            return context;
        }
    }
}
