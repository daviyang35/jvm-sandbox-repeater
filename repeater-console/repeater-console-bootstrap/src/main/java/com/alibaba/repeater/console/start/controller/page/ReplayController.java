package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.domain.ReplayBO;
import com.alibaba.repeater.console.common.params.ReplayParams;
import com.alibaba.repeater.console.service.ReplayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * {@link ReplayController}
 * <p>
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/replay")
public class ReplayController {

    @Resource
    private ReplayService replayService;

    @GetMapping(path = "/")
    public PageResult<ReplayBO> list(@ModelAttribute("params") ReplayParams params) {
        return replayService.list(params);
    }

    @GetMapping(path = "detail")
    public RepeaterResult<ReplayBO> detail(@ModelAttribute("params") ReplayParams params) {
        return replayService.query(params);
    }

    @PostMapping(path = "execute")
    public RepeaterResult<String> replay(@RequestBody ReplayParams params) {
        return replayService.replay(params);
    }

    @RequestMapping("execute.json")
    public RepeaterResult<String> replayModel(@ModelAttribute("requestParams") ReplayParams params) {
        return replayService.replay(params);
    }
}
