package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.MemoryModuleCache;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.nashorn.module.NashornCompileModule;
import org.clever.hinny.nashorn.module.NashornModule;
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
     * 编译模块实现
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
        Assert.notNull(engine, "参数engine不能为空");
        Assert.notNull(rootPath, "参数rootPath不能为空");
        Assert.notNull(moduleCache, "参数moduleCache不能为空");
        Assert.notNull(require, "参数require不能为空");
        Assert.notNull(compileModule, "参数compileModule不能为空");
        Assert.notNull(global, "参数global不能为空");
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
            Assert.notNull(rootPath, "参数rootPath不能为空");
            this.rootPath = rootPath;
        }

        public static Builder create(Folder rootPath) {
            return new Builder(rootPath);
        }

        /**
         * @return NashornScriptEngine
         */
        public NashornScriptEngine getEngine() {
            return engine;
        }

        /**
         * 设置 NashornScriptEngine
         */
        public Builder setEngine(NashornScriptEngine engine) {
            this.engine = engine;
            return this;
        }

        /**
         * @return 自定义引擎全局对象
         */
        public Map<String, Object> getContextMap() {
            return contextMap;
        }

        /**
         * 自定义引擎全局对象
         */
        public Builder setContextMap(Map<String, Object> contextMap) {
            this.contextMap = contextMap;
            return this;
        }

        /**
         * @return 根路径文件夹
         */
        public Folder getRootPath() {
            return rootPath;
        }

        /**
         * @return 模块缓存
         */
        public ModuleCache<ScriptObjectMirror> getModuleCache() {
            return moduleCache;
        }

        /**
         * 设置模块缓存
         */
        public Builder setModuleCache(ModuleCache<ScriptObjectMirror> moduleCache) {
            this.moduleCache = moduleCache;
            return this;
        }

        /**
         * @return 全局require实例(根目录require)
         */
        public Require<ScriptObjectMirror> getRequire() {
            return require;
        }

        /**
         * 设置全局require实例(根目录require)
         */
        public Builder setRequire(Require<ScriptObjectMirror> require) {
            this.require = require;
            return this;
        }

        /**
         * @return 编译模块实现
         */
        public CompileModule<ScriptObjectMirror> getCompileModule() {
            return compileModule;
        }

        /**
         * 设置编译模块实现
         */
        public Builder setCompileModule(CompileModule<ScriptObjectMirror> compileModule) {
            this.compileModule = compileModule;
            return this;
        }

        /**
         * @return 引擎全局变量
         */
        public ScriptObjectMirror getGlobal() {
            return global;
        }

        /**
         * 设置引擎全局变量
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
