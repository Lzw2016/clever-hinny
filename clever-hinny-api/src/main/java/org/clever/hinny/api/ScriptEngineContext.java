package org.clever.hinny.api;

import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.require.Require;

import java.util.Map;

/**
 * 脚本引擎上下文
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:15 <br/>
 *
 * @param <E> script引擎类型
 * @param <T> script引擎对象类型
 */
public interface ScriptEngineContext<E, T> {
    /**
     * 获取script引擎实例
     */
    E getEngine();

    /**
     * 获取Script引擎全局对象
     */
    Map<String, Object> getContextMap();

    /**
     * 根路径文件夹
     */
    Folder getRootPath();

    /**
     * 模块缓存
     */
    ModuleCache<T> getModuleCache();

    /**
     * 全局require实例(根目录require)
     */
    Require<T> getRequire();

    /**
     * 引擎全局变量
     */
    T getGlobal();

    // ...其他Context对象
}
