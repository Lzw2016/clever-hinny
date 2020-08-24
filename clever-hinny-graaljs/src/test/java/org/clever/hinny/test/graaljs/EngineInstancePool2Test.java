package org.clever.hinny.test.graaljs;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.folder.FileSystemFolder;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.graaljs.GraalScriptEngineInstance;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/24 17:01 <br/>
 */
@Slf4j
public class EngineInstancePool2Test {
    private ObjectPool<ScriptEngineInstance<Context, Value>> pool;

    @Before
    public void init() {
        // 创建对象池配置
        GenericObjectPoolConfig<ScriptEngineInstance<Context, Value>> config = new GenericObjectPoolConfig<>();
        config.setMaxWaitMillis(-1);
        config.setMaxTotal(8);
        config.setMinIdle(2);
        // 创建对象工厂
        Folder rootFolder = FileSystemFolder.createRootPath(new File("src/test/resources").getAbsolutePath());
        Engine engine = Engine.newBuilder()
                .useSystemProperties(true)
                .build();
        PooledObjectFactory<ScriptEngineInstance<Context, Value>> factory = new ScriptEngineInstanceFactory(rootFolder, engine);
        // 创建对象池
        pool = new GenericObjectPool<>(factory, config);
    }

    @After
    public void close() {
        pool.close();
    }

    public void usePool() {
        ScriptEngineInstance<Context, Value> instance = null;
        try {
            // 从池中获取对象
            instance = pool.borrowObject();
            // 使用对象
            instance.getContext().getEngine().enter();
            instance.require("/pool2-test").callMember("t01");
            instance.getContext().getEngine().leave();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (instance != null) {
                try {
                    // 出现错误将对象置为失效
                    pool.invalidateObject(instance);
                    // 避免 invalidate 之后再 return 抛异常
                    instance = null;
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        } finally {
            try {
                if (null != instance) {
                    // 使用完后必须 returnObject
                    pool.returnObject(instance);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @SneakyThrows
    @Test
    public void t01() {
        final int threadCount = 10000;
        final Semaphore semaphore = new Semaphore(threadCount);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(256, 256, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(102400));
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            semaphore.acquire();
            executor.execute(() -> {
                usePool();
                semaphore.release();
                // log.info("# --> release");
            });
        }
        semaphore.acquire(threadCount);
        final long endTime = System.currentTimeMillis();
        log.info("# --> 耗时: {}ms", endTime - startTime);
    }
}

@Slf4j
class ScriptEngineInstanceFactory extends BasePooledObjectFactory<ScriptEngineInstance<Context, Value>> {
    private final Folder rootFolder;
    private final Engine engine;

    ScriptEngineInstanceFactory(Folder rootFolder, Engine engine) {
        this.rootFolder = rootFolder;
        this.engine = engine;
    }

    // 创建一个新的对象
    @Override
    public ScriptEngineInstance<Context, Value> create() {
        ScriptEngineInstance<Context, Value> instance = GraalScriptEngineInstance.Builder.create(engine, rootFolder).build();
        log.info("# create");
        // instance.getContext().getEngine().leave();
        return instance;
    }

    // 封装为池化对象
    @Override
    public PooledObject<ScriptEngineInstance<Context, Value>> wrap(ScriptEngineInstance<Context, Value> scriptEngineInstance) {
        return new DefaultPooledObject<>(scriptEngineInstance);
    }

    // 使用完返还对象
    @Override
    public void passivateObject(PooledObject<ScriptEngineInstance<Context, Value>> pooledObject) {
        log.info("# passivateObject");
    }

    // 销毁对象
    @Override
    public void destroyObject(PooledObject<ScriptEngineInstance<Context, Value>> pooledObject) throws Exception {
        pooledObject.getObject().close();
        log.info("# destroyObject");
    }
}