package com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api;

import com.alibaba.jvm.sandbox.repeater.plugin.Constants;
import com.alibaba.jvm.sandbox.repeater.plugin.api.ConfigManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.model.ApplicationModel;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.HttpUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.JSONUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.PropertyUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * {@link DefaultConfigManager} http数据拉取
 * <p>
 *
 * @author zhaoyb1990
 */
public class DefaultConfigManager implements ConfigManager {

    private final static String DEFAULT_CONFIG_URL = PropertyUtil.getPropertyOrDefault(Constants.DEFAULT_CONFIG_DATASOURCE, "");

    @Override
    public RepeaterResult<RepeaterConfig> pullConfig() {
        int retryTime = 100;
        HttpUtil.Resp resp = null;
        while (--retryTime > 0) {
            resp = HttpUtil.doGet(String.format(DEFAULT_CONFIG_URL, ApplicationModel.instance().getAppName(),
                    ApplicationModel.instance().getEnvironment()));
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
