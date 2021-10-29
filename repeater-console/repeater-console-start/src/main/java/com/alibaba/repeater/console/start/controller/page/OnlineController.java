package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.domain.RecordBO;
import com.alibaba.repeater.console.common.domain.RecordDetailBO;
import com.alibaba.repeater.console.common.params.RecordParams;
import com.alibaba.repeater.console.service.RecordService;
import com.alibaba.repeater.console.start.controller.vo.PagerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * {@link OnlineController}
 * <p>
 * 在线流量页面
 *
 * @author zhaoyb1990
 */
@Controller
@RequestMapping("/online")
public class OnlineController {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private RecordService recordService;

    @ResponseBody
    @GetMapping(path = "/")
    public PageResult<RecordBO> onlineRecords(@ModelAttribute RecordParams params) {
        return recordService.query(params);
    }

    @RequestMapping("search.htm")
    public String search(@ModelAttribute("requestParams") RecordParams params, Model model) {
        PageResult<RecordBO> result = recordService.query(params);
        PagerAdapter.transform0(result, model);
        return "online/search";
    }

    @ResponseBody
    @GetMapping(path = "/details")
    public RepeaterResult<RecordDetailBO> details(@ModelAttribute("params") RecordParams params) {
        RepeaterResult<RecordDetailBO> result = recordService.getDetail(params);
        if (Objects.isNull(result)) {
            return RepeaterResult.builder().success(false).message("记录不存在").build();
        }
        return result;
    }

    @RequestMapping("detail.htm")
    public String detail(@ModelAttribute("requestParams") RecordParams params, Model model) {

        RepeaterResult<RecordDetailBO> result = recordService.getDetail(params);
        if (!result.isSuccess()) {
            return "/error/404";
        }
        model.addAttribute("record", result.getData());
        return "online/detail";
    }
}
