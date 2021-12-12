package com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api;

import com.alibaba.jvm.sandbox.repeater.plugin.Constants;
import com.alibaba.jvm.sandbox.repeater.plugin.api.ConsoleManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.PropertyUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class DefaultConsoleManager implements ConsoleManager {
    private static final String RECORD_URL = "/facade/api/record/save";
    private static final String REPEAT_URL = "/facade/api/repeat/save";
    public static final String FACADE_API_RECORD = "/facade/api/record/%s/%s";
    public static final String FACADE_API_CONFIG = "/facade/api/config/%s/%s";
    public static final String MODULE_HEARTBEAT = "/module/report.json";
    public static final String DEFAULT_CONSOLE_URL = "http://localhost:8001/";

    private final String consoleUrl;
    private final String postRecordUrl;
    private final String postRepeatUrl;
    private final String fetchRecordUrl;
    private final String fetchConfigUrl;
    private final String postHeartbeatUrl;

    public DefaultConsoleManager() {
        consoleUrl = PropertyUtil.getPropertyOrDefault(Constants.DEFAULT_CONSOLE_URL, DEFAULT_CONSOLE_URL);
        try {
            final URL baseUrl = new URL(consoleUrl);
            postRecordUrl = new URL(baseUrl, RECORD_URL).toString();
            postRepeatUrl = new URL(baseUrl, REPEAT_URL).toString();
            fetchRecordUrl = new URL(baseUrl, FACADE_API_RECORD).toString();
            fetchConfigUrl = new URL(baseUrl, FACADE_API_CONFIG).toString();
            postHeartbeatUrl = new URL(baseUrl, MODULE_HEARTBEAT).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException("contact console url error", e);
        }
    }

    @Override
    public String consoleUrl() {
        return consoleUrl;
    }

    @Override
    public String postRecordUrl() {
        return postRecordUrl;
    }

    @Override
    public String postRepeatUrl() {
        return postRepeatUrl;
    }

    @Override
    public String fetchRecordUrl(String appName, String traceId) {
        return String.format(fetchRecordUrl, appName, traceId);
    }

    @Override
    public String fetchConfigUrl(String appName, String appEnvironment) {
        return String.format(fetchConfigUrl, appName, appEnvironment);
    }

    @Override
    public String postHeartbeatUrl() {
        return postHeartbeatUrl;
    }
}
