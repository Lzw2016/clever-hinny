package org.clever.hinny.api.module;

import org.clever.hinny.api.ScriptEngineContext;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/18 22:16 <br/>
 *
 * @param <E> script引擎类型
 * @param <T> script引擎对象类型
 */
public abstract class AbstractModule<E, T> implements Module<T> {
    /**
     * 引擎上下文
     */
    protected final ScriptEngineContext<E, T> context;

    public AbstractModule(ScriptEngineContext<E, T> context) {
        this.context = context;
    }

    public ModuleCache<T> getCache() {
        return context.getModuleCache();
    }
}
