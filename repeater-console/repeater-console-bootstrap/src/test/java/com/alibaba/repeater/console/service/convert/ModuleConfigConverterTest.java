package com.alibaba.repeater.console.service.convert;

import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.repeater.console.service.util.JacksonUtil;
import org.junit.Test;

public class ModuleConfigConverterTest {
    @Test
    public void jacksonTest() throws SerializeException {
        final RepeaterConfig deserialize = JacksonUtil.deserialize("{\n" +
                "  \"useTtl\": true,\n" +
                "  \"degrade\": false,\n" +
                "  \"exceptionThreshold\": 1000,\n" +
                "  \"sampleRate\": 10000,\n" +
                "  \"pluginsPath\": null,\n" +
                "  \"httpEntrancePatterns\": [\n" +
                "    \"^/regress/.*$\"\n" +
                "  ],\n" +
                "  \"javaEntranceBehaviors\": [\n" +
                "    {\n" +
                "      \"classPattern\": \"com.alibaba.repeater.console.service.impl.RegressServiceImpl\",\n" +
                "      \"methodPatterns\": [\n" +
                "        \"getRegress\"\n" +
                "      ],\n" +
                "      \"includeSubClasses\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"javaSubInvokeBehaviors\": [\n" +
                "    {\n" +
                "      \"classPattern\": \"com.alibaba.repeater.console.service.impl.RegressServiceImpl\",\n" +
                "      \"methodPatterns\": [\n" +
                "        \"getRegressInner\"\n" +
                "      ],\n" +
                "      \"includeSubClasses\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"classPattern\": \"com.alibaba.repeater.console.service.impl.RegressServiceImpl\",\n" +
                "      \"methodPatterns\": [\n" +
                "        \"findPartner\"\n" +
                "      ],\n" +
                "      \"includeSubClasses\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"classPattern\": \"com.alibaba.repeater.console.service.impl.RegressServiceImpl\",\n" +
                "      \"methodPatterns\": [\n" +
                "        \"slogan\"\n" +
                "      ],\n" +
                "      \"includeSubClasses\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pluginIdentities\": [\n" +
                "    \"http\",\n" +
                "    \"java-entrance\",\n" +
                "    \"java-subInvoke\",\n" +
                "  ],\n" +
                "  \"repeatIdentities\": []\n" +
                "}", RepeaterConfig.class);
        deserialize.toString();
    }
}