package org.clever.hinny.api.module;

import org.clever.hinny.api.ScriptObject;

/**
 * 空缓存(不使用缓存)
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2019/08/27 10:35 <br/>
 */
public class EmptyModuleCache<T extends ScriptObject<?>> implements ModuleCache<T> {

    @Override
    public ModuleInstance<T> get(String fullPath) {
        return null;
    }

    @Override
    public void put(String fullPath, ModuleInstance<T> moduleInstance) {
    }

    @Override
    public void clear() {
    }

    @Override
    public void remove(String fullPath) {
    }
}
