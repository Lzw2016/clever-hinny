package org.clever.hinny.api;

import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.require.Require;

/**
 * 脚本引擎实例
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:15 <br/>
 *
 * @param <E> script引擎类型
 * @param <T> script引擎对象类型
 */
public interface ScriptEngineInstance<E, T> {
    /**
     * 获取脚本引擎上下文
     */
    ScriptEngineContext<E, T> getContext();

    /**
     * 根路径Folder
     */
    Folder getRootPath();

    /**
     * 共享的全局变量
     */
    T getGlobal();

    /**
     * require用于加载其他模块
     */
    Require<T> getRequire();
}

//(x)module                               module变量代表当前模块
//(x)exports                              就是module.exports
//require                                 require用于加载其他模块
//global                                  共享的全局变量
