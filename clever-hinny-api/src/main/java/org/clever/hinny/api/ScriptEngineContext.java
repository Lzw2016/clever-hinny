package org.clever.hinny.api;

import java.util.Map;

/**
 * 脚本引擎上下文
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:15 <br/>
 *
 * @param <T> script引擎对象类型
 */
public interface ScriptEngineContext<T> {
    /**
     * 获取Script引擎全局对象
     */
    Map<String, Object> getContextMap();
}
