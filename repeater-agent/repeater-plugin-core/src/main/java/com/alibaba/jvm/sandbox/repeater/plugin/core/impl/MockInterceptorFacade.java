package com.alibaba.jvm.sandbox.repeater.plugin.core.impl;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.mock.MockRequest;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.mock.MockResponse;
import com.alibaba.jvm.sandbox.repeater.plugin.spi.MockInterceptor;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.ServiceLoader;

/**
 * {@link MockInterceptorFacade}作为{@link MockInterceptor}的包装，负责管理和执行所有的SPI实现
 * <p>
 *
 * @author zhaoyb1990
 */
public class MockInterceptorFacade implements MockInterceptor {

    private static final MockInterceptorFacade INSTANCE = new MockInterceptorFacade();

    public static MockInterceptorFacade instance() {
        return INSTANCE;
    }

    private final List<MockInterceptor> interceptors = Lists.newArrayList();

    private MockInterceptorFacade() {
        ServiceLoader<MockInterceptor> loadedInterceptors = ServiceLoader.load(MockInterceptor.class, this.getClass().getClassLoader());
        for (MockInterceptor interceptor : loadedInterceptors) {
            interceptors.add(interceptor);
        }
    }

    @Override
    public void beforeSelect(final MockRequest request) {
        for (MockInterceptor interceptor : interceptors) {
            if (interceptor.matchingSelect(request)) {
                interceptor.beforeSelect(request);
            }
        }
    }

    @Override
    public void beforeReturn(final MockRequest request, final MockResponse response) {
        for (MockInterceptor interceptor : interceptors) {
            if (interceptor.matchingReturn(request, response)) {
                interceptor.beforeReturn(request, response);
            }
        }
    }

    @Override
    public boolean matchingSelect(final MockRequest request) {
        return false;
    }

    @Override
    public boolean matchingReturn(final MockRequest request, final MockResponse response) {
        return false;
    }
}
