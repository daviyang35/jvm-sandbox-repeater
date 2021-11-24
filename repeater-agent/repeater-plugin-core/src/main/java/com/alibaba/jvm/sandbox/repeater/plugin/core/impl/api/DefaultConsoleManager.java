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
    private static final String REPEAT_URL = "/facade/api/record/save";

    private final String consoleUrl;
    private final String postRecordUrl;
    private final String postRepeatUrl;
    private final String fetchRecordUrl;
    private final String fetchConfigUrl;
    private final String postHeartbeatUrl;

    public DefaultConsoleManager() {
        consoleUrl = PropertyUtil.getPropertyOrDefault(Constants.DEFAULT_CONSOLE_URL, "http://localhost:8001/");
        try {
            final URL baseUrl = new URL(consoleUrl);
            postRecordUrl = new URL(baseUrl, "/facade/api/record/save").toString();
            postRepeatUrl = new URL(baseUrl, "/facade/api/repeat/save").toString();
            fetchRecordUrl = new URL(baseUrl, "/facade/api/record/%s/%s").toString();
            fetchConfigUrl = new URL(baseUrl, "/facade/api/config/%s/%s").toString();
            postHeartbeatUrl = new URL(baseUrl, "/module/report.json").toString();
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
