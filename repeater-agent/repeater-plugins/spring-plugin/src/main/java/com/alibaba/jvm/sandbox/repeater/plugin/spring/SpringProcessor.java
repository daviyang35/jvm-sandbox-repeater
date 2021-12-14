package com.alibaba.jvm.sandbox.repeater.plugin.spring;

import com.alibaba.jvm.sandbox.api.event.BeforeEvent;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultInvocationProcessor;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Identity;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.InvokeType;

import java.util.Collections;

public class SpringProcessor extends DefaultInvocationProcessor {
    public SpringProcessor(InvokeType type) {
        super(type);
    }

    @Override
    public Identity assembleIdentity(BeforeEvent event) {
        return new Identity(InvokeType.JAVA.name(), event.javaClassName, event.javaMethodName + "~" + event.javaMethodDesc, Collections.EMPTY_MAP);
    }
}
