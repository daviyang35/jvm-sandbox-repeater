package com.alibaba.repeater.console.start.controller.page;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleConfigBO;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleConfigParams;
import com.alibaba.repeater.console.service.ModuleConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * {@link ModuleConfigController}
 * <p>
 * 配置管理页面
 *
 * @author zhaoyb1990
 */
@RequestMapping("/config")
@RestController
public class ModuleConfigController {

    @Resource
    private ModuleConfigService moduleConfigService;

    @GetMapping(path = "/")
    public PageResult<ModuleConfigBO> list(@ModelAttribute("params") ModuleConfigParams params) {
        return moduleConfigService.list(params);
    }

    @PostMapping(path = "push")
    public RepeaterResult<ModuleConfigBO> push(@RequestBody ModuleConfigParams params) {
        return moduleConfigService.push(params);
    }

    @PostMapping(path = "saveOrUpdate")
    public RepeaterResult<ModuleConfigBO> add(@RequestBody ModuleConfigParams params) {
        return moduleConfigService.saveOrUpdate(params);
    }

    @DeleteMapping(path = "/{id}")
    public RepeaterResult<Void> delete(@PathVariable("id") Long id) {
        return moduleConfigService.delete(id);
    }

    @GetMapping("detail")
    public RepeaterResult<ModuleConfigBO> detail(@ModelAttribute("params") ModuleConfigParams params) {
        return moduleConfigService.query(params);
    }

//    @RequestMapping("add.htm")
//    public String add(Model model) {
//        RepeaterConfig defaultConf = new RepeaterConfig();
//        List<Behavior> behaviors = Lists.newArrayList();
//        defaultConf.setPluginIdentities(Lists.newArrayList("http", "java-entrance", "java-subInvoke"));
//        defaultConf.setRepeatIdentities(Lists.newArrayList("java", "http"));
//        defaultConf.setUseTtl(true);
//        defaultConf.setHttpEntrancePatterns(Lists.newArrayList("^/regress/.*$"));
//        behaviors.add(new Behavior("com.alibaba.repeater.console.service.impl.RegressServiceImpl", "getRegress"));
//        defaultConf.setJavaEntranceBehaviors(behaviors);
//        List<Behavior> subBehaviors = Lists.newArrayList();
//        subBehaviors.add(new Behavior("com.alibaba.repeater.console.service.impl.RegressServiceImpl", "getRegressInner", "findPartner", "slogan"));
//        defaultConf.setJavaSubInvokeBehaviors(subBehaviors);
//        try {
//            model.addAttribute("config", JacksonUtil.serialize(defaultConf));
//        } catch (SerializeException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "/error/404";
//        }
//        return "config/add";
//    }

    @RequestMapping("saveOrUpdate.json")
    public RepeaterResult<ModuleConfigBO> doAdd(@ModelAttribute("requestParams") ModuleConfigParams params) {
        return moduleConfigService.saveOrUpdate(params);
    }

    @RequestMapping("push.json")
    public RepeaterResult<ModuleConfigBO> pushModel(@ModelAttribute("requestParams") ModuleConfigParams params) {
        return moduleConfigService.push(params);
    }
}
