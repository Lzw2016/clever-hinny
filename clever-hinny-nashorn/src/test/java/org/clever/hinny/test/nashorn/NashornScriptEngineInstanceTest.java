package org.clever.hinny.test.nashorn;

import lombok.extern.slf4j.Slf4j;
import org.clever.hinny.api.ScriptEngineInstance;
import org.clever.hinny.api.ScriptObject;
import org.clever.hinny.api.folder.FileSystemFolder;
import org.clever.hinny.api.folder.Folder;
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
        log.info("### basePath   -> {}", basePath);
        Folder rootFolder = FileSystemFolder.createRootPath(basePath);
        log.info("### rootFolder -> {}", rootFolder);

        ScriptEngineInstance<?, ?> engineInstance = NashornScriptEngineInstance.Builder.create(rootFolder).build();
        log.info("### getEngineName      -> {}", engineInstance.getEngineName());
        log.info("### getEngineVersion   -> {}", engineInstance.getEngineVersion());
        log.info("### getLanguageVersion -> {}", engineInstance.getLanguageVersion());

        ScriptObject<?> scriptObject = engineInstance.require("/01基本使用/01.js");
        log.info("### a1    -> {}", scriptObject.getMember("a1"));
        log.info("### a2    -> {}", scriptObject.getMember("a2"));
        log.info("### fun   -> {}", scriptObject.getMember("fun"));

        log.info("### getMemberNames   -> {}", scriptObject.getMemberNames());
        log.info("### hasMember        -> {}", scriptObject.hasMember("a2"));
        log.info("### hasMember        -> {}", scriptObject.hasMember("a3"));
        scriptObject.setMember("a3", "a333");
        log.info("### hasMember        -> {}", scriptObject.hasMember("a3"));
        log.info("### getMembers       -> {}", scriptObject.getMembers());
        log.info("### size             -> {}", scriptObject.size());
        log.info("### callMember       -> {}", scriptObject.callMember("fun", 1, 2));
    }
}
