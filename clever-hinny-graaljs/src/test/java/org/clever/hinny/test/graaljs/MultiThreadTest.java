package org.clever.hinny.test.graaljs;

import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.folder.FileSystemFolder;
import org.clever.hinny.api.folder.Folder;
import org.clever.hinny.graaljs.GraalScriptEngineInstance;
import org.graalvm.polyglot.Engine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/24 17:45 <br/>
 */
@Slf4j
public class MultiThreadTest {
    private final Folder rootFolder = FileSystemFolder.createRootPath(new File("src/test/resources").getAbsolutePath());
    private ScriptEngineInstance<?, ?> engineInstance;

    @Before
    public void before() {
        // clever-hinny-graaljs
        log.info("### rootFolder -> {}", rootFolder);
        Engine engine = Engine.newBuilder()
                .useSystemProperties(true)
                .build();
        engineInstance = GraalScriptEngineInstance.Builder.create(engine, rootFolder).build();
    }

    @After
    public void after() throws IOException {
        engineInstance.close();
    }

    @Test
    public void t01() throws Exception {
        engineInstance.require("/pool2-test").callMember("t01");

        Thread thread = new Thread(() -> {
            try {
                engineInstance.require("/pool2-test").callMember("t01");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
    }
}
