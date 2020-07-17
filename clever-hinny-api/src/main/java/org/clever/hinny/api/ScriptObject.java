package org.clever.hinny.api;

import java.util.Map;

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

    /**
     * @param key
     * @param value
     */
    void put(String key, Object value);

    /**
     * @param map
     */
    void putAll(Map<String, Object> map);

    /**
     * @param key
     * @param <T>
     */
    <T> T get(String key);

    /**
     * @param key
     * @param <T>
     */
    <T> T remove(String key);
}
