package org.clever.hinny.test.api.folder;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Date;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/25 21:34 <br/>
 */
@Slf4j
public class Tmp {

    @Test
    public void t01() {
        log.info("## -> {}", new Date().toString());
    }

    @Test
    public void t02() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "file/**/*.txt");
        for (Resource resource : resources) {
            log.info(" -> {}", resource.getURL().toExternalForm());
        }

        resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "**/*.txt");
        for (Resource resource : resources) {
            log.info(" -> {}", resource.getURL().toExternalForm());
        }
    }

    @Test
    public void t03() throws IOException {
//     String  "jar:file:D:\\ToolsSoftware\\Maven\\.m2\\repository\\org\\hamcrest\\hamcrest-core\\1.3\\hamcrest-core-1.3.jar!/LICENSE.txt";
    }
}
