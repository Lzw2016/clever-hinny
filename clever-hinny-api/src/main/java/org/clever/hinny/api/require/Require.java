package org.clever.hinny.api.require;

/**
 * CommonJS 接口
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:12 <br/>
 *
 * @param <T> script引擎对象类型
 */
@FunctionalInterface
public interface Require<T> {

    /**
     * CommonJS中的require函数
     *
     * @param id 模块ID(模块路径)
     */
    T require(final String id) throws Exception;
}
