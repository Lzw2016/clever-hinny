package org.clever.hinny.api;

import org.clever.hinny.api.utils.Assert;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/20 21:22 <br/>
 *
 * @param <V> script引擎对象类型
 */
public abstract class AbstractScriptObject<V> implements ScriptObject<V> {
    /**
     * Script引擎对应的对象值
     */
    protected final V original;

    public AbstractScriptObject(V original) {
        Assert.notNull(original, "参数original不能为空");
        this.original = original;
    }

    @Override
    public V originalValue() {
        return original;
    }
}
