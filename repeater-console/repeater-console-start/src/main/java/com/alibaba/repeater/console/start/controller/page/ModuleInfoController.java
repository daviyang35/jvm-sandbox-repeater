package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.service.ModuleInfoService;
import com.alibaba.repeater.console.start.controller.vo.PagerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * {@link ModuleInfoController}
 * <p>
 * 在线模块页面
 *
 * @author zhaoyb1990
 */
@RequestMapping("/module")
@Controller
public class ModuleInfoController {

    @Resource
    private ModuleInfoService moduleInfoService;

    @ResponseBody
    @GetMapping(path = "/")
    public PageResult<ModuleInfoBO> module(@ModelAttribute("params") ModuleInfoParams params) {
        return moduleInfoService.query(params);
    }

    @ResponseBody
    @PostMapping(path = "/frozen")
    public RepeaterResult<ModuleInfoBO> frozen(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.frozen(params);
    }

    @ResponseBody
    @PostMapping(path = "/reload")
    public RepeaterResult<String> reload(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.reload(params);
    }

    @RequestMapping("list.htm")
    public String list(@ModelAttribute("requestParams") ModuleInfoParams params, Model model) {
        PageResult<ModuleInfoBO> result = moduleInfoService.query(params);
        PagerAdapter.transform0(result, model);
        return "module/list";
    }

    @ResponseBody
    @RequestMapping("/byName.json")
    public RepeaterResult<List<ModuleInfoBO>> list(@RequestParam("appName") String appName) {
        return moduleInfoService.query(appName);
    }

    @ResponseBody
    @RequestMapping("/report.json")
    public RepeaterResult<ModuleInfoBO> list(@ModelAttribute("requestParams") ModuleInfoBO params) {
        return moduleInfoService.report(params);
    }

    @ResponseBody
    @RequestMapping("/active.json")
    public RepeaterResult<ModuleInfoBO> active(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.active(params);
    }

    @ResponseBody
    @RequestMapping("/frozen.json")
    public RepeaterResult<ModuleInfoBO> frozenModel(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.frozen(params);
    }

    @ResponseBody
    @RequestMapping("/install.json")
    public RepeaterResult<String> install(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.install(params);
    }

    @ResponseBody
    @RequestMapping("/reload.json")
    public RepeaterResult<String> reloadModel(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.reload(params);
    }
}
