package org.clever.hinny.api.support;

/**
 * CommonJS 接口
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:12 <br/>
 *
 * @param <T> script对象类型
 */
@FunctionalInterface
public interface Require<T> {

    /**
     * CommonJS中的require函数
     *
     * @param path 模块路径
     */
    T require(String path) throws Exception;
}
