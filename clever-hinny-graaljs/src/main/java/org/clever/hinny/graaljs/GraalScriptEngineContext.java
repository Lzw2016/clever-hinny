package org.clever.hinny.graaljs;

import org.clever.hinny.api.AbstractScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.internal.LoggerConsole;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.MemoryModuleCache;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.graaljs.internal.GraalLoggerFactory;
import org.clever.hinny.graaljs.internal.support.GraalObjectToString;
import org.clever.hinny.graaljs.module.GraalCompileModule;
import org.clever.hinny.graaljs.module.GraalModule;
import org.clever.hinny.graaljs.require.GraalRequire;
import org.clever.hinny.graaljs.utils.ScriptEngineUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        private Set<Class<?>> allowAccessClass = new HashSet<>();
        private final Engine graalvmEngine;

        public Builder(Engine graalvmEngine, Folder rootPath) {
            super(rootPath);
            this.graalvmEngine = graalvmEngine;
            // 自定义 contextMap
            LoggerConsole.Instance.setObjectToString(GraalObjectToString.Instance);
            contextMap.put("console", LoggerConsole.Instance);
            contextMap.put("print", LoggerConsole.Instance);
            contextMap.put("LoggerFactory", GraalLoggerFactory.Instance);
        }

        public static Builder create(Engine graalvmEngine, Folder rootPath) {
            return new Builder(graalvmEngine, rootPath);
        }

        /**
         * 增加JavaScript可以访问的Class
         */
        public Builder addAllowAccessClass(Class<?> clazz) {
            if (allowAccessClass != null && clazz != null) {
                allowAccessClass.add(clazz);
            }
            return this;
        }

        /**
         * 设置JavaScript可以访问的Class
         */
        public Builder setAllowAccessClass(Set<Class<?>> allowAccessClass) {
            this.allowAccessClass = allowAccessClass;
            return this;
        }

        /**
         * 创建 ScriptEngineContext
         */
        public GraalScriptEngineContext build() {
            GraalScriptEngineContext context = new GraalScriptEngineContext();
            if (engine == null) {
                Assert.notNull(graalvmEngine, "参数graalvmEngine或者engine不能为空");
                engine = ScriptEngineUtils.creatEngine(graalvmEngine, allowAccessClass);
            }
            context.engine = engine;
            if (contextMap == null) {
                contextMap = Collections.emptyMap();
            }
            context.contextMap = contextMap;
            if (moduleCache == null) {
                moduleCache = new MemoryModuleCache<>();
            }
            context.moduleCache = moduleCache;
            if (require == null) {
                GraalModule mainModule = GraalModule.createMainModule(context);
                require = new GraalRequire(context, mainModule, rootPath);
            }
            context.require = require;
            if (compileModule == null) {
                compileModule = new GraalCompileModule(context);
            }
            context.compileModule = compileModule;
            if (global == null) {
                global = ScriptEngineUtils.newObject(engine);
            }
            context.global = global;
            return context;
        }
    }
}
