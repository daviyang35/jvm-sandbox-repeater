package com.alibaba.repeater.console.service.util;

import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.Serializer;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializerProvider;
import com.alibaba.jvm.sandbox.repeater.plugin.core.wrapper.RecordWrapper;
import com.alibaba.repeater.console.dal.model.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

/**
 * {@link }
 * <p>
 *
 * @author zhaoyb1990
 */
public class ConvertUtil {
    private static final Logger log = LoggerFactory.getLogger(ConvertUtil.class);

    public static Record convertWrapper(RecordWrapper wrapper, String body) {
        Record record = new Record();
        record.setAppName(wrapper.getAppName());
        record.setEnvironment(wrapper.getEnvironment());
        record.setGmtCreate(LocalDateTime.now());

        final Date date = new Date(wrapper.getTimestamp());
        log.info("原时间：{}", date);

        // TODO: setGmtRecord 创建的世间值可能有错
        Instant instant = Instant.ofEpochMilli(wrapper.getTimestamp());
        final LocalDateTime recordDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        record.setGmtRecord(recordDateTime);
        log.info("新时间：{}", recordDateTime);

        record.setHost(wrapper.getHost());
        record.setTraceId(wrapper.getTraceId());
        Serializer hessian = SerializerProvider.instance().provide(Serializer.Type.HESSIAN);
        try {
            Object response = hessian.deserialize(wrapper.getEntranceInvocation().getResponseSerialized(), Object.class);
            if (response instanceof String) {
                record.setResponse(convert2Json((String) response));
            } else {
                record.setResponse(JacksonUtil.serialize(response));
            }
            record.setRequest(JacksonUtil.serialize(hessian.deserialize(wrapper.getEntranceInvocation().getRequestSerialized(), Object[].class)));
        } catch (SerializeException e) {
            // ignore
        }
        record.setEntranceDesc(wrapper.getEntranceDesc());
        record.setWrapperRecord(body);
        return record;
    }

    public static String convert2Json(String json) {
        try {
            return JacksonUtil.serialize(JacksonUtil.deserialize(json, HashMap.class));
        } catch (SerializeException e) {
            return json;
        }
    }
}
