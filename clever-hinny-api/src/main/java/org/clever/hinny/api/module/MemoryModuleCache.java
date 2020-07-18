package org.clever.hinny.api.module;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Module 内存缓存
 *
 * @param <T> script引擎对象类型
 */
public class MemoryModuleCache<T> implements ModuleCache<T> {
    /**
     * 缓存
     */
    private final Cache<String, Module<T>> modulesCache;

    /**
     * @param clearTimeInterval 定时清除缓存的时间间隔，单位：秒(小于0表示不清除)
     */
    public MemoryModuleCache(long clearTimeInterval) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().initialCapacity(521);
        if (clearTimeInterval >= 0) {
            cacheBuilder.expireAfterWrite(clearTimeInterval, TimeUnit.SECONDS);
        }
        this.modulesCache = cacheBuilder.build();
    }

    public MemoryModuleCache() {
        this(-1);
    }

    @Override
    public Module<T> get(String fullPath) {
        return modulesCache.getIfPresent(fullPath);
    }

    @Override
    public void put(String fullPath, Module<T> module) {
        modulesCache.put(fullPath, module);
    }

    @Override
    public void clear() {
        modulesCache.invalidateAll();
    }

    @Override
    public void remove(String fullPath) {
        modulesCache.invalidate(fullPath);
    }
}
