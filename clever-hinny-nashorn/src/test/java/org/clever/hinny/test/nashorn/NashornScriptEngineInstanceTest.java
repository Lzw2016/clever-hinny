package org.clever.hinny.test.nashorn;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.folder.FileSystemFolder;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.nashorn.NashornScriptEngineContext;
import org.clever.hinny.nashorn.NashornScriptEngineInstance;
import org.junit.Test;

import java.io.File;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/19 09:21 <br/>
 */
@Slf4j
public class NashornScriptEngineInstanceTest {

    @Test
    public void t01() throws Exception {
        String basePath = new File("src/test/resources/javascript").getAbsolutePath();
        log.info("### basePath -> {}", basePath);
        Folder rootFolder = FileSystemFolder.createRootPath(basePath);
        log.info("### rootFolder -> {}", rootFolder);
        NashornScriptEngineContext context = NashornScriptEngineContext.Builder.create(rootFolder).build();
        NashornScriptEngineInstance engineInstance = new NashornScriptEngineInstance(context);

        ScriptObjectMirror scriptObjectMirror = engineInstance.getRequire().require("/01基本使用/01.js");

        log.info("### scriptObjectMirror -> {}", scriptObjectMirror);
        log.info("### engineInstance -> {}", engineInstance);
    }
}
