package com.alibaba.jvm.sandbox.repeater.plugin.core.model;

import com.alibaba.jvm.sandbox.repeater.plugin.core.util.ExceptionAware;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.PropertyUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * {@link ApplicationModel} 描述一个基础应用模型
 * <p>
 * 应用名    {@link ApplicationModel#appName}
 * 机器名    {@link ApplicationModel#host}
 * 环境信息  {@link ApplicationModel#environment}
 * 模块配置  {@link ApplicationModel#config}
 * </p>
 *
 * @author zhaoyb1990
 */
public class ApplicationModel {
    public static final String DEFAULT_ENV = "default";
    public static final String DEFAULT_NAME = "unknown";
    public static final String REPEATER_APP_NAME = "REPEATER_APP_NAME";
    public static final String REPEATER_APP_ENV = "REPEATER_APP_ENV";

    private String appName;

    private String environment;

    private String host;

    private volatile RepeaterConfig config;

    private ExceptionAware exceptionAware = new ExceptionAware();

    private volatile boolean fusing = false;

    private static ApplicationModel instance = new ApplicationModel();

    private ApplicationModel() {
        this.appName = PropertyUtil.getPropertyOrDefault(REPEATER_APP_NAME, DEFAULT_NAME);
        this.environment = PropertyUtil.getPropertyOrDefault(REPEATER_APP_ENV, DEFAULT_ENV);
        try {
            this.host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // default value for disaster
            this.host = "127.0.0.1";
        }
    }

    public static ApplicationModel instance() {
        return instance;
    }

    /**
     * 是否正在工作（熔断机制）
     *
     * @return true/false
     */
    public boolean isWorkingOn() {
        return !fusing;
    }

    /**
     * 是否降级（系统行为）
     *
     * @return true/false
     */
    public boolean isDegrade() {
        return config == null || config.isDegrade();
    }

    /**
     * 异常阈值检测
     *
     * @param throwable 异常类型
     */
    public void exceptionOverflow(Throwable throwable) {
        if (exceptionAware.exceptionOverflow(throwable, config == null ? 1000 : config.getExceptionThreshold())) {
            fusing = true;
            exceptionAware.printErrorLog();
        }
    }

    public Integer getSampleRate() {
        return config == null ? 0 : config.getSampleRate();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public RepeaterConfig getConfig() {
        return config;
    }

    public void setConfig(RepeaterConfig config) {
        this.config = config;
    }

    public ExceptionAware getExceptionAware() {
        return exceptionAware;
    }

    public void setExceptionAware(ExceptionAware exceptionAware) {
        this.exceptionAware = exceptionAware;
    }

    public boolean isFusing() {
        return fusing;
    }

    public void setFusing(boolean fusing) {
        this.fusing = fusing;
    }
}
