package org.clever.hinny.test.api.folder;

import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.internal.LoggerConsole;
import org.junit.Test;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/25 23:17 <br/>
 */
@Slf4j
public class LoggerConsoleTest {
    @Test
    public void t01() {
        LoggerConsole console = LoggerConsole.Instance;
        String label = "aa";

        console.count();
        console.count(label);
        console.count();
        console.count(label);

        console.time();
        console.time(label);
        console.timeLog(null, "T111 ", "2222 ");
        console.timeLog(label, "T333 ", "444 ");
        console.timeEnd();
        console.timeEnd(label);
    }

    @Test
    public void t02() {
        LoggerConsole console = LoggerConsole.Instance;
        console.log("123");
        console.trace("123");
        console.debug("123");
        console.info("123");
        console.warn("123");
        console.error("123");
    }
}
