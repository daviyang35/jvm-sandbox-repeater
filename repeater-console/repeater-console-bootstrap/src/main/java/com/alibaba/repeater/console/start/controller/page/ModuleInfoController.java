package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.service.ModuleInfoService;
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
@RestController
public class ModuleInfoController {

    @Resource
    private ModuleInfoService moduleInfoService;

    @GetMapping(path = "/")
    public PageResult<ModuleInfoBO> module(@ModelAttribute("params") ModuleInfoParams params) {
        return moduleInfoService.query(params);
    }

    @PostMapping(path = "/frozen")
    public RepeaterResult<ModuleInfoBO> frozen(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.frozen(params);
    }

    @PostMapping(path = "/active")
    public RepeaterResult<ModuleInfoBO> active(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.active(params);
    }

    @PostMapping(path = "/reload")
    public RepeaterResult<String> reload(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.reload(params);
    }

    @PostMapping(path = "/install")
    public RepeaterResult<String> install(@RequestBody ModuleInfoParams params) {
        return moduleInfoService.install(params);
    }

    @DeleteMapping(path = "/{id}")
    public RepeaterResult<Void> delete(@PathVariable("id") Long id) {
        return moduleInfoService.delete(id);
    }

    @RequestMapping("/byName.json")
    public RepeaterResult<List<ModuleInfoBO>> list(@RequestParam("appName") String appName) {
        return moduleInfoService.query(appName);
    }

    @RequestMapping("/report.json")
    public RepeaterResult<ModuleInfoBO> list(@ModelAttribute("requestParams") ModuleInfoBO params) {
        return moduleInfoService.report(params);
    }

    @RequestMapping("/active.json")
    public RepeaterResult<ModuleInfoBO> activeModel(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.active(params);
    }

    @RequestMapping("/frozen.json")
    public RepeaterResult<ModuleInfoBO> frozenModel(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.frozen(params);
    }

    @RequestMapping("/install.json")
    public RepeaterResult<String> installModel(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.install(params);
    }

    @RequestMapping("/reload.json")
    public RepeaterResult<String> reloadModel(@ModelAttribute("requestParams") ModuleInfoParams params) {
        return moduleInfoService.reload(params);
    }
}
