package org.clever.hinny.api.module;

import org.clever.hinny.api.ScriptEngineContext;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/18 22:55 <br/>
 */
public abstract class AbstractCompileModule<E, T> implements CompileModule<T> {
    /**
     * 引擎上下文
     */
    protected final ScriptEngineContext<E, T> context;

    public AbstractCompileModule(ScriptEngineContext<E, T> context) {
        this.context = context;
    }

    public ModuleCache<T> getCache() {
        return context.getModuleCache();
    }

    /**
     * 获取Script模块代码
     *
     * @param code Script代码
     */
    protected String getModuleScriptCode(String code) {
        return "(function(exports, require, module, __filename, __dirname) {\n" + code + "\n});";
    }
}
