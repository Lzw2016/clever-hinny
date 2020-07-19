package org.clever.hinny.api.require;

import org.clever.hinny.api.ScriptEngineContext;
import org.clever.hinny.api.module.ModuleCache;
import org.clever.hinny.api.utils.Assert;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/17 09:01 <br/>
 *
 * @param <E> script引擎类型
 * @param <T> script引擎对象类型
 */
public abstract class AbstractRequire<E, T> implements Require<T> {
    /**
     * 引擎上下文
     */
    protected final ScriptEngineContext<E, T> context;

    public AbstractRequire(ScriptEngineContext<E, T> context) {
        Assert.notNull(context, "参数context不能为空");
        this.context = context;
    }

    public ModuleCache<T> getCache() {
        return context.getModuleCache();
    }
}

//require():                              加载外部模块
//(x)require.resolve()：                  将模块名解析到一个绝对路径
//(x)require.main：                       指向主模块
//(*)require.cache：                      指向所有缓存的模块
//(x)require.extensions：                 根据文件的后缀名，调用不同的执行函数