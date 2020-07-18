package org.clever.hinny.api.require;

import org.clever.hinny.api.module.ModuleCache;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/17 09:01 <br/>
 */
public abstract class AbstractRequire<T> implements Require<T> {
    /**
     * 模块缓存
     */
    protected final ModuleCache<T> moduleCache;

    public AbstractRequire(ModuleCache<T> moduleCache) {
        this.moduleCache = moduleCache;
    }

    public ModuleCache<T> getCache() {
        return moduleCache;
    }
}

//require():                              加载外部模块
//(x)require.resolve()：                  将模块名解析到一个绝对路径
//(x)require.main：                       指向主模块
//(*)require.cache：                      指向所有缓存的模块
//(x)require.extensions：                 根据文件的后缀名，调用不同的执行函数