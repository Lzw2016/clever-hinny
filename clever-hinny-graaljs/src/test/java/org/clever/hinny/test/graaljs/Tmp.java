package org.clever.hinny.test.graaljs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.hinny.graaljs.GraalConstant;
import org.clever.hinny.graaljs.utils.ScriptEngineUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
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
        // Object arr = new String[]{"111", "222", "333"};
        Object arr = new int[]{111, 222, 333};
        Collection<?> collection = Arrays.asList((int[]) arr);
        logger.info("### collection -> {}", collection.size());
        for (Object o : collection) {
            logger.info("### o -> {}", o);
        }
        logger.info("###111 -> {}", collection);
        logger.info("###111 -> {}", new Date().toString());
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

    @Test
    public void t05() {
        Context context = Context.newBuilder(GraalConstant.Js_Language_Id).build();
        byte a = 1;
        short b = 2;
        int c = 3;
        long d = 4;
        double e = 5;
        boolean f = false;
        char g = 'a';
        String h = "abcd";
        Date i = new Date();
        BigDecimal j = new BigDecimal("1354741344987654323456765434567564564568989.564948989745189789454894894864");
        List<String> k = new ArrayList<>() {{
            add("111");
            add("222");
            add("333");
        }};
        Set<String> l = new HashSet<>() {{
            add("111");
            add("222");
            add("333");
        }};
        Map<String, Object> m = new HashMap<>() {{
            put("int", 1);
            put("boolean", false);
            put("str", "asdfghjkl");
        }};
        log.info("## -> {}", context.asValue(a));
        log.info("## -> {}", context.asValue(b));
        log.info("## -> {}", context.asValue(c));
        log.info("## -> {}", context.asValue(d));
        log.info("## -> {}", context.asValue(e));
        log.info("## -> {}", context.asValue(f));
        log.info("## -> {}", context.asValue(g));
        log.info("## -> {}", context.asValue(h));
        log.info("## -> {}", context.asValue(i));
        log.info("## -> {}", context.asValue(j));
        log.info("## -> {}", context.asValue(k));
        log.info("## -> {}", context.asValue(l));
        log.info("## -> {}", context.asValue(m));
        log.info("## -> {}", context.asValue(m).getMemberKeys().size()); // size=0
    }

    @Test
    public void t06() {
        Context context = Context.newBuilder(GraalConstant.Js_Language_Id).build();
        Value value = ScriptEngineUtils.newArray(context, "aaa", 111, false);
        log.info("## -> {}", value);

        Object object = new Object[]{"aaa", 111, false};
        value = ScriptEngineUtils.newArray(context, object);
        log.info("## -> {}", value);

        log.info("## -> {}", object);

//        Map<Integer, Boolean> map = new HashMap<>();
//        TypeVariable<? extends Class<?>>[] parameters = map.getClass().getTypeParameters();
//        log.info("## parameters -> {}", parameters);
    }
}