package org.clever.hinny.test.api.watch;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.clever.hinny.api.watch.FileSystemWatcher;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

import java.io.File;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/30 12:00 <br/>
 */
@Slf4j
public class FileSystemWatcherTest {

    @Test
    public void t01() throws InterruptedException {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(
                "D:\\SourceCode\\clever\\clever-hinny-js\\",
                new String[]{"*.js"},
                new String[]{},
                IOCase.SYSTEM,
                monitorEvent -> log.info("#### --> {} | {}", monitorEvent.getEventType(), monitorEvent.getFileOrDir().getAbsolutePath()),
                1000,
                100
        );
        fileSystemWatcher.start();
        Thread.sleep(1000);
        fileSystemWatcher.stop();
        fileSystemWatcher.start();
        Thread.sleep(1000 * 60 * 5);
    }

    @Test
    public void t02() {
        // ？匹配一个字符
        // *匹配0个或多个字符
        String path = "D:\\SourceCode\\clever\\clever-hinny-js\\01Base.ts";
        log.info("res -> {}", FilenameUtils.wildcardMatch(path, "*.ts", IOCase.SYSTEM));
        log.info("res -> {}", FilenameUtils.wildcardMatch(path, "*\\clever\\*", IOCase.SYSTEM));
    }

    @Test
    public void t03() {
        // ？匹配一个字符
        // *匹配0个或多个字符
        // **匹配0个或多个目录
        String path = "D:\\SourceCode\\clever\\clever-hinny-js\\01Base.ts";
        AntPathMatcher matcher = new AntPathMatcher(File.separator);
        log.info("res -> {}", matcher.match("*\\**\\*.ts", path));
        log.info("res -> {}", matcher.match("*\\**\\clever\\**", path));
    }
}
