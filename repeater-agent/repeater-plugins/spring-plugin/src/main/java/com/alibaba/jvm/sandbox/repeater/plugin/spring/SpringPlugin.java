package com.alibaba.jvm.sandbox.repeater.plugin.spring;

import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.repeater.plugin.api.InvocationProcessor;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.AbstractInvokePluginAdapter;
import com.alibaba.jvm.sandbox.repeater.plugin.core.model.EnhanceModel;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.InvokeType;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.jvm.sandbox.repeater.plugin.exception.PluginLifeCycleException;
import com.alibaba.jvm.sandbox.repeater.plugin.spi.InvokePlugin;
import com.google.common.collect.Lists;
import org.kohsuke.MetaInfServices;

import java.util.List;

@MetaInfServices(InvokePlugin.class)
public class SpringPlugin extends AbstractInvokePluginAdapter {
    @Override
    protected List<EnhanceModel> getEnhanceModels() {
        EnhanceModel springService = EnhanceModel.builder()
                .classPattern("com.aos.*")
                .classAnnotations(new String[]{
                        "org.springframework.stereotype.Service"
                })
                .methodPatterns(EnhanceModel.MethodPattern.transform("*"))
                .watchTypes(Event.Type.BEFORE, Event.Type.RETURN, Event.Type.THROWS)
                .build();

        EnhanceModel springController = EnhanceModel.builder()
                .classPattern("com.aos.*")
                .classAnnotations(new String[]{
                        "org.springframework.stereotype.Controller"
                })
                .methodPatterns(EnhanceModel.MethodPattern.transform("*"))
                .watchTypes(Event.Type.BEFORE, Event.Type.RETURN, Event.Type.THROWS)
                .build();

        EnhanceModel springRestController = EnhanceModel.builder()
                .classPattern("com.aos.*")
                .classAnnotations(new String[]{
                        "org.springframework.web.bind.annotation.RestController"
                })
                .methodPatterns(EnhanceModel.MethodPattern.transform("*"))
                .watchTypes(Event.Type.BEFORE, Event.Type.RETURN, Event.Type.THROWS)
                .build();

        return Lists.newArrayList(springService, springController, springRestController);
    }

    @Override
    protected InvocationProcessor getInvocationProcessor() {
        return new SpringProcessor(getType());
    }

    @Override
    public InvokeType getType() {
        return InvokeType.SPRING;
    }

    @Override
    public String identity() {
        return "spring";
    }

    @Override
    public boolean isEntrance() {
        return false;
    }

    @Override
    public void onConfigChange(RepeaterConfig config) throws PluginLifeCycleException {
        super.onConfigChange(config);
    }
}
