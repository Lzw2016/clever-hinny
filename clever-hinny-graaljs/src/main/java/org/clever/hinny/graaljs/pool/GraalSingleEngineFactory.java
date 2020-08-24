package org.clever.hinny.graaljs.pool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.clever.hinny.api.GlobalConstant;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.api.utils.Assert;
import org.clever.hinny.graaljs.GraalConstant;
import org.clever.hinny.graaljs.GraalScriptEngineInstance;
import org.clever.hinny.graaljs.utils.ScriptEngineUtils;
import org.graalvm.polyglot.*;

import java.io.Closeable;
import java.util.HashSet;
import java.util.Set;

/**
 * 单个Engine对象的 GraalScriptEngineInstance 创建工厂
 * <p>
 * 作者：lizw <br/>
 * 创建时间：2020/08/24 19:24 <br/>
 */
@Slf4j
public class GraalSingleEngineFactory extends BasePooledObjectFactory<ScriptEngineInstance<Context, Value>> implements Closeable {
    private final Folder rootFolder;
    private final Engine engine;
    private final HostAccess hostAccess;

    public GraalSingleEngineFactory(Folder rootFolder, Engine engine) {
        Assert.notNull(rootFolder, "参数rootFolder不能为空");
        Assert.notNull(engine, "参数engine不能为空");
        this.rootFolder = rootFolder;
        this.engine = engine;
        this.hostAccess = creatHostAccess();
    }

    // TODO 子类可扩展
    protected HostAccess creatHostAccess() {
        // 沙箱环境控制 - 定义JavaScript可以访问的Class(使用黑名单机制)
        HostAccess.Builder hostAccessBuilder = HostAccess.newBuilder();
        hostAccessBuilder.allowArrayAccess(true);
        hostAccessBuilder.allowListAccess(true);
        hostAccessBuilder.allowPublicAccess(true);
        hostAccessBuilder.allowAllImplementations(true);
        // 计划支持 https://github.com/graalvm/graaljs/issues/143
        // hostAccessBuilder.allowMapAccess(true);
        Set<Class<?>> denyAccessClass = new HashSet<>(GlobalConstant.Default_Deny_Access_Class);
        ScriptEngineUtils.addDenyAccess(hostAccessBuilder, denyAccessClass);
        return hostAccessBuilder.build();
    }

    // TODO 子类可扩展
    protected Context creatContext() {
        Assert.notNull(engine, "参数engine不能为空");
        Context.Builder contextBuilder = Context.newBuilder(GraalConstant.Js_Language_Id)
                .engine(engine)
                .options(ScriptEngineUtils.Context_Default_Options)
                // 不允许使用实验特性
                .allowExperimentalOptions(false)
                // 不允许多语言访问
                .allowPolyglotAccess(PolyglotAccess.NONE)
                // 默认允许所有行为
                .allowAllAccess(true)
                // 不允许JavaScript创建进程
                .allowCreateProcess(false)
                // 不允许JavaScript创建线程
                .allowCreateThread(false)
                // 不允许JavaScript访问环境变量
                .allowEnvironmentAccess(EnvironmentAccess.NONE)
                // 不允许JavaScript对主机的IO操作
                .allowIO(false)
                // 不允许JavaScript访问本机接口
                .allowNativeAccess(false)
                // 不允许JavaScript加载Class
                .allowHostClassLoading(false)
                // 定义JavaScript可以加载的Class
                // .allowHostClassLookup()
                // 定义JavaScript可以访问的Class
                // .allowHostAccess(HostAccess.ALL)
                // 限制JavaScript的资源使用(CPU)
                // .resourceLimits()
                ;
        // 沙箱环境控制 - 定义JavaScript可以访问的Class(使用黑名单机制)
        contextBuilder.allowHostAccess(hostAccess);
        // 沙箱环境控制 - 限制JavaScript的资源使用
        // ResourceLimits resourceLimits = ResourceLimits.newBuilder().statementLimit()
        return contextBuilder.build();
    }

    /**
     * 创建一个新的对象
     */
    @Override
    public ScriptEngineInstance<Context, Value> create() {
        // TODO 子类可扩展
        ScriptEngineInstance<Context, Value> instance = GraalScriptEngineInstance.Builder.create(engine, rootFolder)
                .setEngine(creatContext())
                .build();
        log.info("# create");
        // instance.getContext().getEngine().leave();
        return instance;
    }

    /**
     * 封装为池化对象
     */
    @Override
    public PooledObject<ScriptEngineInstance<Context, Value>> wrap(ScriptEngineInstance<Context, Value> obj) {
        return new DefaultPooledObject<>(obj);
    }

    /**
     * 验证对象是否可用
     */
    @Override
    public boolean validateObject(PooledObject<ScriptEngineInstance<Context, Value>> p) {
        log.info("# validateObject");
        return super.validateObject(p);
    }

    /**
     * 激活对象，从池中取对象时会调用此方法
     */
    @Override
    public void activateObject(PooledObject<ScriptEngineInstance<Context, Value>> p) throws Exception {
        log.info("# activateObject");
        super.activateObject(p);
    }

    /**
     * 钝化对象，向池中返还对象时会调用此方法
     */
    @Override
    public void passivateObject(PooledObject<ScriptEngineInstance<Context, Value>> p) throws Exception {
        log.info("# passivateObject");
        super.passivateObject(p);
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<ScriptEngineInstance<Context, Value>> p) throws Exception {
        log.info("# destroyObject");
        p.getObject().close();
    }

    /**
     * 释放 Engine 对象
     */
    public void close() {
        engine.close();
    }
}