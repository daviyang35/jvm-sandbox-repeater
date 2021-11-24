package com.alibaba.jvm.sandbox.repeater.plugin.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@UtilityClass
@Slf4j
public class JSONUtil {
    private final static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();

        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        mapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        mapper.enable(JsonParser.Feature.ALLOW_TRAILING_COMMA);

        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        mapper.registerModule(new JavaTimeModule());
    }

    public static <T> T toBean(String content, Class<T> valueType) throws JsonProcessingException {
        return mapper.readValue(content, valueType);
    }

    public static <T> T toBean(byte[] bytes, Class<T> valueType) throws IOException {
        return mapper.readValue(bytes, valueType);
    }

    public static Object toBean(byte[] bytes) throws IOException {
        return mapper.readValue(bytes, Object.class);
    }

    public static <T> T toBean(String content, TypeReference<T> reference) throws JsonProcessingException {
        return mapper.readValue(content, reference);
    }

    public static <T> T toBean(File file, Class<T> valueType) throws IOException {
        return mapper.readValue(file, valueType);
    }

    public static byte[] toBytes(Object object) throws JsonProcessingException {
        return mapper.writeValueAsBytes(object);
    }

    public static String toJSONString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("serialize object error.", e);
            return "";
        }
    }
}
