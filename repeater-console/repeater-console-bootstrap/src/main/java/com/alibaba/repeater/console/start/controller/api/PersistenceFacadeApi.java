package com.alibaba.repeater.console.start.controller.api;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeatModel;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.service.RecordService;
import com.alibaba.repeater.console.service.ReplayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * {@link PersistenceFacadeApi} Demo工程；作为repeater录制回放的数据存储
 * <p>
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/facade/api")
public class PersistenceFacadeApi {

    @Resource
    private RecordService recordService;
    @Resource
    private ReplayService replayService;

    @GetMapping(path = "record/{appName}/{traceId}")
    public RepeaterResult<String> getWrapperRecord(@PathVariable("appName") String appName,
                                                   @PathVariable("traceId") String traceId) {
        return recordService.get(appName, traceId);
    }

    @GetMapping(path = "repeat/{appName}/{ip}/{traceId}")
    public RepeaterResult<String> repeat(@PathVariable("appName") String appName,
                                         @PathVariable("ip") String ip,
                                         @PathVariable("traceId") String traceId,
                                         HttpServletRequest request) {
        // fix issue #63
        ReplayParams params = ReplayParams.builder()
                .repeatId(request.getHeader("RepeatId"))
                .ip(ip)
                .build();
        params.setAppName(appName);
        params.setTraceId(traceId);
        return replayService.replay(params);
    }

    @PostMapping(path = "record/save")
    public RepeaterResult<String> recordSave(@RequestBody String body) {
        return recordService.saveRecord(body);
    }

    @PostMapping(path = "repeat/save")
    public RepeaterResult<String> repeatSave(@RequestBody String body) {
        return replayService.saveRepeat(body);
    }

    @GetMapping(path = "repeat/callback/{repeatId}")
    public RepeaterResult<RepeatModel> callback(@PathVariable("repeatId") String repeatId) {
        return recordService.callback(repeatId);
    }

}
