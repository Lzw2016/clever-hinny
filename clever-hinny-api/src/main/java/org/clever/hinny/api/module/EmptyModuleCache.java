package org.clever.hinny.api.module;

/**
 * 空缓存(不使用缓存)
 *
 * @param <T> script引擎对象类型
 */
public class EmptyModuleCache<T> implements ModuleCache<T> {

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
