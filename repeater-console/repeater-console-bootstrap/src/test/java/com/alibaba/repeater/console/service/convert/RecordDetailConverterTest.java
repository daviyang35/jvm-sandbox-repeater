package com.alibaba.repeater.console.service.convert;

import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.Serializer;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializerProvider;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.RecordWrapper;
import com.alibaba.repeater.console.common.domain.InvocationBO;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecordDetailConverterTest {
    InvocationConverter invocationConverter = new InvocationConverter();

    @Test
    void test() throws SerializeException, IOException {
        final File file = ResourceUtils.getFile("classpath:wrapper.txt");
        final byte[] bytes = FileCopyUtils.copyToByteArray(file);

        Serializer hessian = SerializerProvider.instance().provide(Serializer.Type.HESSIAN);
        RecordWrapper wrapper = hessian.deserialize(new String(bytes), RecordWrapper.class);
        final List<InvocationBO> subInvocation = Optional.ofNullable(wrapper.getSubInvocations())
                .orElse(Collections.emptyList())
                .stream().map(invocationConverter::convert)
                .collect(Collectors.toList());
        final Serializer provide = SerializerProvider.instance().provide(Serializer.Type.JSON);
        final String s = provide.serialize2String(subInvocation);

//        final String serialize = JacksonUtil.serialize(subInvocation);
    }
}