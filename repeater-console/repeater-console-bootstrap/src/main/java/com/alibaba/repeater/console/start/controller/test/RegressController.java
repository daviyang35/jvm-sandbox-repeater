package com.alibaba.repeater.console.start.controller.test;

import com.alibaba.jvm.sandbox.repeater.plugin.core.util.ExecutorInner;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.JSONUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterResult;
import com.alibaba.repeater.console.common.domain.Regress;
import com.alibaba.repeater.console.service.RegressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * {@link RegressController} 持续回归demo服务
 * <p>
 *
 * @author zhaoyb1990
 */
@RestController
@RequestMapping("/regress")
public class RegressController {

    @Resource
    private RegressService regressService;


    /**
     * 单线程java示例
     *
     * @param name 名字
     * @return
     */
    @GetMapping(path = "/partner/{name}")
    public String partner(@PathVariable("name") String name) {
        String male = "李雷";
        String female = "韩梅梅";
        RepeaterResult<String> partner = regressService.findPartner(name);
        if (partner.getData().equals(female) && male.equals(name)) {
            return "天哪!李雷和韩梅梅终于在一起了~";
        }
        return name + "成功匹配到小伙伴[" + partner.getData() + "]!";
    }


    /**
     * 单线程java示例
     *
     * @return
     */
    @GetMapping(path = "/slogan")
    public String slogan() {
        return "<h1 align=\"center\" style=\"color:red;margin-top:300px\">" + regressService.slogan() + "</h1>";
    }

    /**
     * 单线程java示例
     *
     * @param name 名字
     * @return
     */
    @GetMapping(path = "/get/{name}")
    public RepeaterResult<Regress> get(@PathVariable("name") String name) {
        return regressService.getRegress(name);
    }

    /**
     * 单线程java示例
     *
     * @param name 名字
     * @return
     */
    @GetMapping(path = "/getWithCache/{name}")
    public RepeaterResult<Regress> getWithCache(@PathVariable("name") String name) {
        return regressService.getRegressWithCache(name);
    }

    /**
     * 多线程并发示例
     *
     * @param name  名字
     * @param count 梳理
     * @return
     */
    @GetMapping(path = "/get/{name}/{count}")
    public RepeaterResult<List<Regress>> get(@PathVariable("name") String name,
                                             @PathVariable("count") int count) {
        return regressService.getRegress(name, count);
    }

    /**
     * 异步servlet
     *
     * @param name    名字
     * @param request request
     * @return
     */
    @GetMapping(path = "/getAsync/{name}")
    public RepeaterResult<Regress> queryByNameAsync(final @PathVariable("name") String name,
                                                    final HttpServletRequest request) {
        final AsyncContext context = request.startAsync();
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) {
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                ServletResponse response = event.getAsyncContext().getResponse();
                PrintWriter out = response.getWriter();
                out.write("TimeOut in Processing");
                out.flush();
                out.close();
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                ServletResponse response = event.getAsyncContext().getResponse();
                PrintWriter out = response.getWriter();
                out.write("Error on processing");
                out.flush();
                out.close();
            }

            @Override
            public void onStartAsync(AsyncEvent event) {
            }
        });
        context.setTimeout(1000);
        ExecutorInner.execute(new Runnable() {
            @Override
            public void run() {
                RepeaterResult<Regress> pr = regressService.getRegress(name);
                try {
                    PrintWriter out = context.getResponse().getWriter();
                    context.getResponse().setCharacterEncoding("UTF-8");
                    context.getResponse().setContentType("application/json");
                    out.write(JSONUtil.toJSONString(pr));
                    out.flush();
                } catch (Exception e) {
                    // ignore
                }
                context.complete();
            }
        });
        return null;
    }
}
