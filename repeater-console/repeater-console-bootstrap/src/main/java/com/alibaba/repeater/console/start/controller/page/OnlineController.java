package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.domain.RecordBO;
import com.alibaba.repeater.console.common.domain.RecordDetailBO;
import com.alibaba.repeater.console.common.params.RecordParams;
import com.alibaba.repeater.console.service.RecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * {@link OnlineController}
 * <p>
 * 在线流量页面
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/online")
public class OnlineController {

    @Resource
    private RecordService recordService;


    @GetMapping(path = "/")
    public PageResult<RecordBO> onlineRecords(@ModelAttribute RecordParams params) {
        return recordService.query(params);
    }

    @GetMapping(path = "/details")
    public RepeaterResult<RecordDetailBO> details(@ModelAttribute("params") RecordParams params) {
        RepeaterResult<RecordDetailBO> result = recordService.getDetail(params);
        if (Objects.isNull(result)) {
            return RepeaterResult.builder().success(false).message("记录不存在").build();
        }
        return result;
    }
}
