package org.clever.hinny.test.base;

import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.folder.FileSystemFolder;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.graaljs.GraalScriptEngineInstance;
import org.clever.hinny.nashorn.NashornScriptEngineInstance;
import org.graalvm.polyglot.Engine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 09:00 <br/>
 */
@Slf4j
public class T01Console {

    private final Folder rootFolder = FileSystemFolder.createRootPath(new File("src/test/resources/base").getAbsolutePath());

    private ScriptEngineInstance<?, ?> engineInstance;

    private ScriptObject<?> scriptObject;

    @Before
    public void before1() throws Exception {
        // clever-hinny-graaljs
        log.info("### rootFolder -> {}", rootFolder);
        Engine engine = Engine.newBuilder()
                .useSystemProperties(true)
                .build();
        engineInstance = GraalScriptEngineInstance.Builder.create(engine, rootFolder).build();
        scriptObject = engineInstance.require("/T01Console");
    }

    //@Before
    public void before2() {
        // clever-hinny-nashorn
        log.info("### rootFolder -> {}", rootFolder);
        engineInstance = NashornScriptEngineInstance.Builder.create(rootFolder).build();
    }

    @After
    public void after() throws IOException {
        engineInstance.close();
    }

    @Test
    public void t01() {
        scriptObject.callMember("log_1");
        log.info("#-----------------------------------------------------------------------------------");
    }

    @Test
    public void t02() {
        scriptObject.callMember("count_1");
        log.info("#-----------------------------------------------------------------------------------");
    }

    @Test
    public void t03() {
        scriptObject.callMember("time_1");
        log.info("#-----------------------------------------------------------------------------------");
    }

    @Test
    public void t04() {
        scriptObject.callMember("log_2");
        log.info("#-----------------------------------------------------------------------------------");
    }
}
