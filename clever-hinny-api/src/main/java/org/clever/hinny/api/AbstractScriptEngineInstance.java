package org.clever.hinny.api;

import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.utils.Assert;

import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:27 <br/>
 *
 * @param <E> script引擎类型
 * @param <T> script引擎对象类型
 */
public abstract class AbstractScriptEngineInstance<E, T> implements ScriptEngineInstance<E, T> {
    /**
     * 引擎上下文
     */
    protected final ScriptEngineContext<E, T> context;

    public AbstractScriptEngineInstance(ScriptEngineContext<E, T> context) {
        Assert.notNull(context, "参数context不能为空");
        this.context = context;
    }

    @Override
    public ScriptEngineContext<E, T> getContext() {
        return context;
    }

    @Override
    public Folder getRootPath() {
        return context.getRootPath();
    }

    @Override
    public T getGlobal() {
        return context.getGlobal();
    }

    @Override
    public Require<T> getRequire() {
        return context.getRequire();
    }

    @Override
    public ScriptObject<T> require(String id) throws Exception {
        T scriptObject = context.getRequire().require(id);
        return newScriptObject(scriptObject);
    }

    /**
     * 创建ScriptObject
     */
    protected abstract ScriptObject<T> newScriptObject(T scriptObject);

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

        /**
         * 创建 ScriptEngineContext
         */
        public abstract ScriptEngineInstance<E, T> build();
    }
}
