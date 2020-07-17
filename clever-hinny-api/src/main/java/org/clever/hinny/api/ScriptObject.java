package org.clever.hinny.api;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 09:59 <br/>
 *
 * @param <V> script引擎对象类型
 */
public interface ScriptObject<V> {

    /**
     * 获取script引擎对象
     */
    V getValue();
}
