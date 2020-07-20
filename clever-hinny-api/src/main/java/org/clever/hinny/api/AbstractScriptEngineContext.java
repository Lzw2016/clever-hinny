package org.clever.hinny.api;

import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:08 <br/>
 *
 * @param <E> script引擎类型
 * @param <T> script引擎对象类型
 */
public abstract class AbstractScriptEngineContext<E, T> implements ScriptEngineContext<E, T> {
    /**
     * NashornScriptEngine
     */
    protected E engine;
    /**
     * 自定义引擎全局对象
     */
    protected Map<String, Object> contextMap = new ConcurrentHashMap<>();
    /**
     * 根路径文件夹
     */
    protected Folder rootPath;
    /**
     * 模块缓存
     */
    protected ModuleCache<T> moduleCache;
    /**
     * 全局require实例(根目录require)
     */
    protected Require<T> require;
    /**
     * 编译模块实现
     */
    protected CompileModule<T> compileModule;
    /**
     * 引擎全局变量
     */
    protected T global;

    protected AbstractScriptEngineContext() {
    }

    public AbstractScriptEngineContext(
            E engine,
            Map<String, Object> contextMap,
            Folder rootPath,
            ModuleCache<T> moduleCache,
            Require<T> require,
            CompileModule<T> compileModule,
            T global) {
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
    public E getEngine() {
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
    public ModuleCache<T> getModuleCache() {
        return moduleCache;
    }

    @Override
    public Require<T> getRequire() {
        return require;
    }

    @Override
    public CompileModule<T> getCompileModule() {
        return compileModule;
    }

    @Override
    public T getGlobal() {
        return global;
    }

    public static abstract class AbstractBuilder<E, T> {
        protected E engine;
        protected Map<String, Object> contextMap;
        protected final Folder rootPath;
        protected ModuleCache<T> moduleCache;
        protected Require<T> require;
        protected CompileModule<T> compileModule;
        protected T global;

        /**
         * @param rootPath 根路径文件夹
         */
        public AbstractBuilder(Folder rootPath) {
            Assert.notNull(rootPath, "参数rootPath不能为空");
            this.rootPath = rootPath;
        }

        /**
         * @return NashornScriptEngine
         */
        public E getEngine() {
            return engine;
        }

        /**
         * 设置 NashornScriptEngine
         */
        public AbstractBuilder<E, T> setEngine(E engine) {
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
        public AbstractBuilder<E, T> setContextMap(Map<String, Object> contextMap) {
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
        public ModuleCache<T> getModuleCache() {
            return moduleCache;
        }

        /**
         * 设置模块缓存
         */
        public AbstractBuilder<E, T> setModuleCache(ModuleCache<T> moduleCache) {
            this.moduleCache = moduleCache;
            return this;
        }

        /**
         * @return 全局require实例(根目录require)
         */
        public Require<T> getRequire() {
            return require;
        }

        /**
         * 设置全局require实例(根目录require)
         */
        public AbstractBuilder<E, T> setRequire(Require<T> require) {
            this.require = require;
            return this;
        }

        /**
         * @return 编译模块实现
         */
        public CompileModule<T> getCompileModule() {
            return compileModule;
        }

        /**
         * 设置编译模块实现
         */
        public AbstractBuilder<E, T> setCompileModule(CompileModule<T> compileModule) {
            this.compileModule = compileModule;
            return this;
        }

        /**
         * @return 引擎全局变量
         */
        public T getGlobal() {
            return global;
        }

        /**
         * 设置引擎全局变量
         */
        public AbstractBuilder<E, T> setGlobal(T global) {
            this.global = global;
            return this;
        }

        public abstract ScriptEngineContext<E, T> build();
    }
}
