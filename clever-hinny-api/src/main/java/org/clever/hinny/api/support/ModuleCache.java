package org.clever.hinny.api.support;

import org.clever.hinny.api.ScriptModule;

/**
 * 脚本模块缓存
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/07/14 11:10 <br/>
 */
public interface ModuleCache {

    /**
     * 从缓存中获取脚本模块，不存在就返回null
     *
     * @param path 模块路径
     */
    ScriptModule get(Path path);

    /**
     * 缓存脚本模块
     *
     * @param path         模块路径
     * @param scriptModule 脚本模块
     */
    void put(Path path, ScriptModule scriptModule);

    /**
     * 清空脚本模块缓存
     */
    void clear();

    /**
     * 删除脚本模块缓存
     *
     * @param path 模块路径
     */
    void remove(Path path);
}
