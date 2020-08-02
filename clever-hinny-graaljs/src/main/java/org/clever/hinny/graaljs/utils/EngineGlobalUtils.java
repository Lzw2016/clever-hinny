package org.clever.hinny.graaljs.utils;

import org.clever.hinny.api.internal.LoggerConsole;
import org.clever.hinny.graaljs.internal.GraalInterop;
import org.clever.hinny.graaljs.internal.GraalJObject;
import org.clever.hinny.graaljs.internal.GraalLoggerFactory;
import org.clever.hinny.graaljs.internal.support.GraalObjectToString;

import java.util.Map;

public class EngineGlobalUtils {

    public static void putGlobalObjects(Map<String, Object> contextMap) {
        LoggerConsole.Instance.setObjectToString(GraalObjectToString.Instance);
        contextMap.put("console", LoggerConsole.Instance);
        contextMap.put("print", LoggerConsole.Instance);
        contextMap.put("LoggerFactory", GraalLoggerFactory.Instance);
        contextMap.put("JObject", GraalJObject.Instance);
        contextMap.put("Interop", GraalInterop.Instance);
    }
}
