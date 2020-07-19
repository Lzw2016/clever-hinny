package org.clever.hinny.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.CompileModule;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;
import org.clever.hinny.api.utils.Assert;

import javax.script.Bindings;
import javax.script.ScriptContext;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 21:25 <br/>
 */
public class NashornScriptEngineInstance implements ScriptEngineInstance<NashornScriptEngine, ScriptObjectMirror> {
    /**
     * 引擎上下文
     */
    private final ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context;

    public NashornScriptEngineInstance(ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> context) {
        Assert.notNull(context, "参数context不能为空");
        this.context = context;
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
    public ScriptEngineContext<NashornScriptEngine, ScriptObjectMirror> getContext() {
        return context;
    }

    @Override
    public Folder getRootPath() {
        return context.getRootPath();
    }

    @Override
    public ScriptObjectMirror getGlobal() {
        return context.getGlobal();
    }

    @Override
    public Require<ScriptObjectMirror> getRequire() {
        return context.getRequire();
    }

    @Override
    public ScriptObject<ScriptObjectMirror> require(String id) throws Exception {
        ScriptObjectMirror scriptObjectMirror = context.getRequire().require(id);
        return new NashornScriptObject(scriptObjectMirror);
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
        public NashornScriptEngineInstance build() {
            NashornScriptEngineContext context = NashornScriptEngineContext.Builder.create(rootPath)
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