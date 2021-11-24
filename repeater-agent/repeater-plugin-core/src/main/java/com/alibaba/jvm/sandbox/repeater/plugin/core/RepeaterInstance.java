package com.alibaba.jvm.sandbox.repeater.plugin.core;

import com.alibaba.jvm.sandbox.repeater.plugin.api.Broadcaster;
import com.alibaba.jvm.sandbox.repeater.plugin.api.ConfigManager;
import com.alibaba.jvm.sandbox.repeater.plugin.api.ConsoleManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultBroadcaster;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultConfigManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultConsoleManager;

/**
 * {@link RepeaterInstance} 配置单例
 *
 * @author daviyang35
 */
public class RepeaterInstance {

    private static RepeaterInstance instance = new RepeaterInstance();

    private ConfigManager configManager;

    private Broadcaster broadcaster;

    private ConsoleManager consoleManager;

    public static RepeaterInstance instance() {
        return instance;
    }

    private RepeaterInstance() {
        consoleManager = new DefaultConsoleManager();
        configManager = new DefaultConfigManager(consoleManager);
        broadcaster = new DefaultBroadcaster(consoleManager);
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

    public String postHeartbeatUrl() {
        return instance().getConsoleManager().postHeartbeatUrl();
    }
}

