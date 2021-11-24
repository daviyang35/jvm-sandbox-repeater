package com.alibaba.repeater.console.service.impl;

import com.alibaba.jvm.sandbox.repeater.plugin.core.util.HttpUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.ModuleInfoBO;
import com.alibaba.repeater.console.common.domain.ModuleStatus;
import com.alibaba.repeater.console.common.domain.PageResult;
import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.dal.dao.ModuleInfoDao;
import com.alibaba.repeater.console.dal.model.ModuleInfo;
import com.alibaba.repeater.console.service.ModuleInfoService;
import com.alibaba.repeater.console.service.convert.ModuleInfoConverter;
import com.alibaba.repeater.console.service.util.ResultHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ModuleInfoServiceImpl}
 * <p>
 *
 * @author zhaoyb1990
 */
@Service("heartbeatService")
public class ModuleInfoServiceImpl implements ModuleInfoService {

    private static final String activeURI = "http://%s:%s/sandbox/default/module/http/sandbox-module-mgr/active?ids=repeater";

    private static final String frozenURI = "http://%s:%s/sandbox/default/module/http/sandbox-module-mgr/frozen?ids=repeater";

    @Value("${repeat.reload.url}")
    private String reloadURI;

    private static final String installBash = "sh %s/sandbox/bin/sandbox.sh -p %s -P 8820";

    @Resource
    private ModuleInfoDao moduleInfoDao;

    @Resource
    private ModuleInfoConverter moduleInfoConverter;

    @Override
    public PageResult<ModuleInfoBO> query(ModuleInfoParams params) {
        Page<ModuleInfo> page = moduleInfoDao.selectByParams(params);

        PageResult<ModuleInfoBO> result = new PageResult<>();
        result.setSuccess(true);
        result.setPageIndex(params.getPage());
        result.setCount(page.getTotalElements());
        result.setPageSize(params.getSize());
        result.setTotalPage(page.getTotalPages());
        result.setData(page.getContent().stream().map(moduleInfoConverter::convert).collect(Collectors.toList()));
        return result;
    }

    @Override
    public RepeaterResult<List<ModuleInfoBO>> query(String appName) {
        List<ModuleInfo> byAppName = moduleInfoDao.findByAppName(appName);
        if (CollectionUtils.isEmpty(byAppName)) {
            return ResultHelper.fail("data not exist");
        }

        final List<ModuleInfoBO> collect = byAppName.stream().
                map(moduleInfoConverter::convert)
                .collect(Collectors.toList());
        return ResultHelper.success(collect);
    }

    @Override
    public RepeaterResult<ModuleInfoBO> query(String appName, String environment) {
        ModuleInfo moduleInfo = moduleInfoDao.findByAppNameAndEnvironment(appName, environment);
        if (moduleInfo == null) {
            return RepeaterResult.builder().message("data not exist").build();
        }
        return ResultHelper.success(moduleInfoConverter.convert(moduleInfo));
    }

    @Override
    public RepeaterResult<ModuleInfoBO> report(ModuleInfoBO params) {
        ModuleInfo moduleInfo = moduleInfoConverter.reconvert(params);
        final LocalDateTime date = LocalDateTime.now();
        moduleInfo.setGmtModified(date);
        moduleInfo.setGmtCreate(date);
        moduleInfoDao.save(moduleInfo);
        return ResultHelper.success(moduleInfoConverter.convert(moduleInfo));
    }

    @Override
    public RepeaterResult<ModuleInfoBO> active(ModuleInfoParams params) {
        return execute(activeURI, params, ModuleStatus.ACTIVE);
    }

    @Override
    public RepeaterResult<ModuleInfoBO> frozen(ModuleInfoParams params) {
        return execute(frozenURI, params, ModuleStatus.FROZEN);
    }

    @Override
    public RepeaterResult<String> install(ModuleInfoParams params) {
        // this is a fake local implement; must be overwritten in product usage;
        String runtimeBeanName = ManagementFactory.getRuntimeMXBean().getName();
        String pid = runtimeBeanName.split("@")[0];
        BufferedReader input = null;
        BufferedReader error = null;
        PrintWriter output = null;
        try {
            // /Users/tom/sandbox/bin/sandbox.sh
            String[] path = StringUtils.split(System.getProperty("user.dir"), File.separator);

            String userDir = File.separator + path[0] + File.separator + path[1];
            Process process = Runtime.getRuntime().exec(String.format(installBash, userDir, pid));
            input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            output = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line).append("\n");
            }
            while ((line = error.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return ResultHelper.success("operate success", builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // ignore
                }
            }
            if (output != null) {
                output.close();
            }
            if (error != null) {
                try {
                    error.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return ResultHelper.fail();
    }

    @Override
    public RepeaterResult<String> reload(ModuleInfoParams params) {
        ModuleInfo moduleInfo = moduleInfoDao.findByAppNameAndEnvironmentAndIp(params.getAppName(), params.getEnvironment(), params.getIp());
        if (moduleInfo == null) {
            return ResultHelper.fail("data not exist");
        }
        HttpUtil.Resp resp = HttpUtil.doGet(String.format(reloadURI, moduleInfo.getIp(), moduleInfo.getPort()));
        return ResultHelper.fs(resp.isSuccess());
    }

    @Override
    public RepeaterResult<Void> delete(Long id) {
        try {
            moduleInfoDao.delete(id);
            return ResultHelper.success();
        } catch (Exception e) {
            return ResultHelper.fail("删除错误");
        }
    }

    private RepeaterResult<ModuleInfoBO> execute(String uri, ModuleInfoParams params, ModuleStatus finishStatus) {
        ModuleInfo moduleInfo = moduleInfoDao.findByAppNameAndEnvironment(params.getAppName(), params.getEnvironment());
        if (moduleInfo == null) {
            return ResultHelper.fail("data not exist");
        }
        HttpUtil.Resp resp = HttpUtil.doGet(String.format(uri, moduleInfo.getIp(), moduleInfo.getPort()));
        if (!resp.isSuccess()) {
            return ResultHelper.fail(resp.getMessage());
        }

        return ResultHelper.success(moduleInfoConverter.convert(moduleInfo));
    }
}
