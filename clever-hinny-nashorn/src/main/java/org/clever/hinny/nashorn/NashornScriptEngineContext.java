package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.EmptyModuleCache;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.nashorn.module.NashornCompileModule;
import org.clever.hinny.nashorn.require.NashornRequire;
import org.clever.hinny.nashorn.utils.ScriptEngineUtils;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:53 <br/>
 */
public class NashornScriptEngineContext implements ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> {
    /**
     * NashornScriptEngine
     */
    private NashornScriptEngine engine;
    /**
     * 自定义引擎全局对象
     */
    private Map<String, Object> contextMap = new ConcurrentHashMap<>();
    /**
     * 根路径文件夹
     */
    private Folder rootPath;
    /**
     * 模块缓存
     */
    private ModuleCache<ScriptObjectMirror> moduleCache;
    /**
     * 全局require实例(根目录require)
     */
    private Require<ScriptObjectMirror> require;
    /**
     * 编译脚本成ScriptModule
     */
    private CompileModule<ScriptObjectMirror> compileModule;
    /**
     * 引擎全局变量
     */
    private ScriptObjectMirror global;

    private NashornScriptEngineContext() {
    }

    public NashornScriptEngineContext(
            NashornScriptEngine engine,
            Map<String, Object> contextMap,
            Folder rootPath,
            ModuleCache<ScriptObjectMirror> moduleCache,
            Require<ScriptObjectMirror> require,
            CompileModule<ScriptObjectMirror> compileModule,
            ScriptObjectMirror global) {
        // TODO 参数校验
        this.engine = engine;
        if (contextMap != null) {
            this.contextMap.putAll(contextMap);
        }
        this.rootPath = rootPath;
        this.moduleCache = moduleCache;
        this.require = require;
        this.compileModule = compileModule;
        this.global = global;
    }

    @Override
    public NashornScriptEngine getEngine() {
        return engine;
    }

    @Override
    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    @Override
    public Folder getRootPath() {
        return rootPath;
    }

    @Override
    public ModuleCache<ScriptObjectMirror> getModuleCache() {
        return moduleCache;
    }

    @Override
    public Require<ScriptObjectMirror> getRequire() {
        return require;
    }

    @Override
    public CompileModule<ScriptObjectMirror> getCompileModule() {
        return compileModule;
    }

    @Override
    public ScriptObjectMirror getGlobal() {
        return global;
    }

    public static class Builder {
        private NashornScriptEngine engine;
        private Map<String, Object> contextMap;
        private final Folder rootPath;
        private ModuleCache<ScriptObjectMirror> moduleCache;
        private Require<ScriptObjectMirror> require;
        private CompileModule<ScriptObjectMirror> compileModule;
        private ScriptObjectMirror global;

        /**
         * @param rootPath 根路径文件夹
         */
        public Builder(Folder rootPath) {
            this.rootPath = rootPath;
        }

        /**
         * @return
         */
        public NashornScriptEngine getEngine() {
            return engine;
        }

        /**
         * @param engine
         * @return
         */
        public Builder setEngine(NashornScriptEngine engine) {
            this.engine = engine;
            return this;
        }

        /**
         * @return
         */
        public Map<String, Object> getContextMap() {
            return contextMap;
        }

        /**
         * @param contextMap
         * @return
         */
        public Builder setContextMap(Map<String, Object> contextMap) {
            this.contextMap = contextMap;
            return this;
        }

        /**
         * @return
         */
        public Folder getRootPath() {
            return rootPath;
        }

        /**
         * @return
         */
        public ModuleCache<ScriptObjectMirror> getModuleCache() {
            return moduleCache;
        }

        /**
         * @param moduleCache
         * @return
         */
        public Builder setModuleCache(ModuleCache<ScriptObjectMirror> moduleCache) {
            this.moduleCache = moduleCache;
            return this;
        }

        /**
         * @return
         */
        public Require<ScriptObjectMirror> getRequire() {
            return require;
        }

        /**
         * @param require
         * @return
         */
        public Builder setRequire(Require<ScriptObjectMirror> require) {
            this.require = require;
            return this;
        }

        /**
         * @return
         */
        public CompileModule<ScriptObjectMirror> getCompileModule() {
            return compileModule;
        }

        /**
         * @param compileModule
         * @return
         */
        public Builder setCompileModule(CompileModule<ScriptObjectMirror> compileModule) {
            this.compileModule = compileModule;
            return this;
        }

        /**
         * @return
         */
        public ScriptObjectMirror getGlobal() {
            return global;
        }

        /**
         * @param global
         * @return
         */
        public Builder setGlobal(ScriptObjectMirror global) {
            this.global = global;
            return this;
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
                moduleCache = new EmptyModuleCache<>();
            }
            if (require == null) {
                require = new NashornRequire(context, null, rootPath);
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
