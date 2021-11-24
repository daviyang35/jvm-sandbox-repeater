package com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api;

import com.alibaba.jvm.sandbox.repeater.plugin.api.ConfigManager;
import com.alibaba.jvm.sandbox.repeater.plugin.api.ConsoleManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.model.ApplicationModel;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.HttpUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.JSONUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link DefaultConfigManager} http数据拉取
 * <p>
 *
 * @author zhaoyb1990
 */
@Slf4j
public class DefaultConfigManager implements ConfigManager {

    private final ConsoleManager consoleManager;

    public DefaultConfigManager(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    @Override
    public RepeaterResult<RepeaterConfig> pullConfig() {
        int retryTime = 5;
        HttpUtil.Resp resp = null;
        while (--retryTime > 0) {
            final String fetchConfigUrl = consoleManager.fetchConfigUrl(
                    ApplicationModel.instance().getAppName(),
                    ApplicationModel.instance().getEnvironment());
            log.debug("fetchConfigUrl:{}", fetchConfigUrl);
            resp = HttpUtil.doGet(fetchConfigUrl);
            if (resp.isSuccess()) {
                break;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // ignore
                break;
            }
        }
        try {
            return JSONUtil.toBean(resp.getBody(), new TypeReference<RepeaterResult<RepeaterConfig>>() {
            });
        } catch (Exception e) {
            return RepeaterResult.builder().success(false).message(e.getMessage()).build();
        }
    }

}
