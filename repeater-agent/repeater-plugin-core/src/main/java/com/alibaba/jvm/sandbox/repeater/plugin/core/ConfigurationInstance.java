package com.alibaba.jvm.sandbox.repeater.plugin.core;

import com.alibaba.jvm.sandbox.repeater.plugin.api.Broadcaster;
import com.alibaba.jvm.sandbox.repeater.plugin.api.ConfigManager;
import com.alibaba.jvm.sandbox.repeater.plugin.api.ConsoleManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultBroadcaster;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultConfigManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultConsoleManager;

/**
 * {@link ConfigurationInstance} 配置单例
 *
 * @author daviyang35
 */
public class ConfigurationInstance {

    private static ConfigurationInstance instance = new ConfigurationInstance();

    private ConfigManager configManager;

    private Broadcaster broadcaster;

    private ConsoleManager consoleManager;

    public static ConfigurationInstance instance() {
        return instance;
    }

    private ConfigurationInstance() {
        configManager = new DefaultConfigManager();
        broadcaster = new DefaultBroadcaster();
        consoleManager = new DefaultConsoleManager();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }
}

