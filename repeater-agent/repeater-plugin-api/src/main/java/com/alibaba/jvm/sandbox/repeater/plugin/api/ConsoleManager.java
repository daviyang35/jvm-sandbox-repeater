package com.alibaba.jvm.sandbox.repeater.plugin.api;

/**
 * {@link ConsoleManager} 控制台配置信息管理器
 * <p>
 *
 * @author daviyang35
 */
public interface ConsoleManager {
    String consoleUrl();

    String postRecordUrl();

    String postRepeatUrl();

    String fetchRecordUrl(String appName, String traceId);

    String fetchConfigUrl(String appName, String appEnvironment);

    String postHeartbeatUrl();
}
