package com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api;

import com.alibaba.jvm.sandbox.repeater.plugin.api.ConsoleManager;

public class DefaultConsoleManager implements ConsoleManager {
    private String serverUrl;

    public DefaultConsoleManager() {
    }

    @Override
    public String configDataSourceURL() {
        return null;
    }
}
