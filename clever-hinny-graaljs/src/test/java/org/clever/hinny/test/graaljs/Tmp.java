package org.clever.hinny.test.graaljs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.hinny.graaljs.GraalConstant;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/25 14:18 <br/>
 */
@Slf4j
public class Tmp {
    @Test
    public void t01() {
        // Engine engine = Engine.create();
        // Context.getCurrent()
        Context context = Context.newBuilder(GraalConstant.Js_Language_Id).build();
        String jsCode = "(function() { var b = x; return function() { return 1 + b; };});";
        Value function = context.eval(GraalConstant.Js_Language_Id, jsCode);
        context.getBindings(GraalConstant.Js_Language_Id).putMember("x", 10);
        Value res = function.execute();
        log.info("### res -> {}", res.execute());
        context.getBindings(GraalConstant.Js_Language_Id).putMember("x", 100);
    }

    @Test
    public void t02() {
        Context context = Context.newBuilder(GraalConstant.Js_Language_Id).allowAllAccess(true).build();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        context.getBindings(GraalConstant.Js_Language_Id).putMember("x", atomicInteger);
        String jsCode = "(function() { while (true) { x.incrementAndGet(); } });";
        Value function = context.eval(GraalConstant.Js_Language_Id, jsCode);
        new Thread(() -> {
            try {
                Thread.sleep(1_000);
                context.close(true);
                log.info("### context.close()");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            function.executeVoid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            log.info("### -> {}", atomicInteger.get());
        }
        log.info("### end");
    }

    @Test
    public void t03() {
        Logger logger = LoggerFactory.getLogger(StringUtils.EMPTY);
        logger.info("###111");
    }

    @Test
    public void t04() {
        Context context_1 = Context.newBuilder(GraalConstant.Js_Language_Id).build();
        Value value = context_1.eval(GraalConstant.Js_Language_Id, "new Date()");
        Context context_2 = Context.newBuilder(GraalConstant.Js_Language_Id).build();
        Value fuc = context_2.eval(GraalConstant.Js_Language_Id, "(function(obj) { return JSON.stringify(obj); });");
        Object object = fuc.execute(value);
        log.info("# -> {}", object);
    }
}
