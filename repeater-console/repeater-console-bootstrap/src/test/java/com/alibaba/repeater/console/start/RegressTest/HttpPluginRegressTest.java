package com.alibaba.repeater.console.start.RegressTest;

import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializeException;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.Serializer;
import com.alibaba.jvm.sandbox.repeater.plugin.core.serialize.SerializerProvider;
import com.alibaba.jvm.sandbox.repeater.plugin.core.trace.TraceGenerator;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.HttpUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Behavior;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import com.alibaba.repeater.console.start.DataProvider.HttpPluginTestDataProvider;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@link HttpPluginRegressTest} 集成测试；依赖于通过bootstrap.sh启动console启动
 * <p>
 *
 * @author zhaoyb1990
 */
@Slf4j
public class HttpPluginRegressTest {

    private RepeaterConfig getConfig() {
        RepeaterConfig defaultConf = new RepeaterConfig();
        defaultConf.setUseTtl(true);
        defaultConf.setHttpEntrancePatterns(Lists.newArrayList("^/regress/.*$"));
        defaultConf.setPluginIdentities(Lists.newArrayList("http", "java-entrance", "java-subInvoke", "mybatis", "ibatis"));
        defaultConf.setRepeatIdentities(Lists.newArrayList("java", "http"));

        List<Behavior> behaviors = Lists.newArrayList();
        behaviors.add(new Behavior("com.alibaba.repeater.console.service.impl.RegressServiceImpl", "getRegress"));
        defaultConf.setJavaEntranceBehaviors(behaviors);

        List<Behavior> subBehaviors = Lists.newArrayList();
        subBehaviors.add(new Behavior("com.alibaba.repeater.console.service.impl.RegressServiceImpl", "getRegressInner", "findPartner", "slogan"));
        defaultConf.setJavaSubInvokeBehaviors(subBehaviors);

        return defaultConf;
    }

    @BeforeClass
    public void beforeClass() throws SerializeException, InterruptedException {
        final RepeaterConfig config = getConfig();
        Serializer serializer = SerializerProvider.instance().provide(Serializer.Type.HESSIAN);
        final String hessianConfigString = serializer.serialize2String(config);
        HashMap<String, String> params = new HashMap<>(2);
        params.put("_data", hessianConfigString);
        HttpUtil.doGet("http://127.0.0.1:8820/sandbox/default/module/http/repeater/pushConfig", params);
        // 等待初始化完成
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
    }

    @Test(description = "Used to test httpPlugin' record and repeat ",
            dataProvider = "HttpPluginTestDataProvider",
            dataProviderClass = HttpPluginTestDataProvider.class
    )
    public void httpPluginRecordRepeatTest(String url) {
        try {
            String traceId = TraceGenerator.generate();
            // record
            HttpUtil.Resp record = executeRecord(url, traceId, null);
            Assert.assertNotNull(record);
            Assert.assertTrue(record.isSuccess());
            Thread.sleep(200);
            // repeat
            HttpUtil.Resp repeat = executeRepeat(url, traceId, null);
            Assert.assertNotNull(record);
            Assert.assertTrue(record.isSuccess());
            Assert.assertEquals(record.getBody(), repeat.getBody());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    private HttpUtil.Resp executeRecord(String uri, String traceId, Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>(2);
        }
        params.put("Repeat-TraceId", traceId);
        final String url = "http://127.0.0.1:8001/regress" + uri;
        log.info("executeRecord:{} traceId:{}", url, traceId);
        return HttpUtil.doGet(url, params);
    }

    private HttpUtil.Resp executeRepeat(String uri, String traceId, Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>(2);
        }
        params.put("Repeat-TraceId-X", traceId);
        final String url = "http://127.0.0.1:8001/regress" + uri;
        log.info("executeRepeat:{} traceId:{}", url, traceId);
        return HttpUtil.doGet(url, params);
    }
}
