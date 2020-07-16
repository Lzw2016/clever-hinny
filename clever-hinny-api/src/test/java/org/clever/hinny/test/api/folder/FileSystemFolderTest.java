package org.clever.hinny.test.api.folder;

import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.folder.FileSystemFolder;
import org.junit.Test;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/16 13:36 <br/>
 */
@Slf4j
public class FileSystemFolderTest {

    @Test
    public void t01() {
        FileSystemFolder folder = FileSystemFolder.createRootPath("D:\\SourceCode\\clever\\clever-hinny\\clever-hinny-api");
        log.info("### toString -> {}", folder.toString());
        log.info("### isFile   -> {}", folder.isFile());
        log.info("### isDir    -> {}", folder.isDir());
        log.info("### exists   -> {}", folder.exists());
    }
}
