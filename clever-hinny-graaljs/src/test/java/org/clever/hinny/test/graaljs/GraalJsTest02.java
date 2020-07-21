package org.clever.hinny.test.graaljs;

import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.graaljs.GraalConstant;
import org.graalvm.polyglot.Context;
import org.junit.Test;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/21 10:06 <br/>
 */
@Slf4j
public class GraalJsTest02 {

    @Test
    public void t01() {
        Context context = Context.newBuilder(GraalConstant.Js_Language_Id)
                .option("js.ecmascript-version", "6")
                .build();

        log.info("### --> {}", context.getEngine().getVersion());
        log.info("### --> {}", context.getEngine().getImplementationName());

        log.info("-----------------------------------------------------------------------------------------------------------------------------------");
        
        context.getEngine().getOptions().forEach(option -> {
            log.info("### {} | {} | {}", option.getStability(), option.getName(), option.getHelp());
        });
        context.getEngine().getLanguages().entrySet().forEach(entry -> {
            entry.getValue().getOptions().forEach(option -> {
                log.info("### {} | {} | {} | {}", option.getStability(), option.getName(), option.getHelp(), option.getCategory());
            });
        });
        context.getEngine().getInstruments().entrySet().forEach(entry -> {
            entry.getValue().getOptions().forEach(option -> {
                log.info("### {} | {} | {}", option.getStability(), option.getName(), option.getHelp());
            });
        });
        context.close();
    }
}
