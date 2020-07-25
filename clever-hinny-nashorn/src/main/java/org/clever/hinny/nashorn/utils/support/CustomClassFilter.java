package org.clever.hinny.nashorn.utils.support;

import jdk.nashorn.api.scripting.ClassFilter;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/25 10:10 <br/>
 */
public class CustomClassFilter implements ClassFilter {
    public final Set<String> allowAccessClass;

    public CustomClassFilter(Set<Class<?>> allowAccessClass) {
        this.allowAccessClass = allowAccessClass == null ? Collections.emptySet() : allowAccessClass.stream().map(Class::getName).collect(Collectors.toSet());
    }

    @Override
    public boolean exposeToScripts(String className) {
        return allowAccessClass.contains(className);
    }
}
