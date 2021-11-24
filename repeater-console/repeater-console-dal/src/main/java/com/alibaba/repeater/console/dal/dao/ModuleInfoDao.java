package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.common.params.ModuleInfoParams;
import com.alibaba.repeater.console.dal.model.ModuleInfo;
import com.alibaba.repeater.console.dal.repository.ModuleInfoRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * {@link }
 * <p>
 *
 * @author zhaoyb1990
 */
@Component("moduleInfoDao")
public class ModuleInfoDao {
    private final Logger log = LoggerFactory.getLogger(ModuleInfoDao.class);

    @Resource
    private ModuleInfoRepository moduleInfoRepository;

    public List<ModuleInfo> findByAppName(String appName) {
        return moduleInfoRepository.findByAppName(appName);
    }

    public Page<ModuleInfo> selectByParams(@NotNull final ModuleInfoParams params) {
        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getSize(), Sort.Direction.DESC, "id");
        return moduleInfoRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (params.getAppName() != null && !params.getAppName().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("appName"), params.getAppName()));
                    }
                    if (params.getIp() != null && !params.getIp().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("ip"), params.getIp()));
                    }
                    if (params.getEnvironment() != null && !params.getEnvironment().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("environment"), params.getEnvironment()));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                },
                pageable
        );
    }

    public ModuleInfo save(ModuleInfo params) {
        if (moduleInfoRepository.updateByAppNameAndEnvironment(params) > 0) {
            return params;
        }
        return moduleInfoRepository.saveAndFlush(params);
    }

    public ModuleInfo saveAndFlush(ModuleInfo params) {
        return moduleInfoRepository.saveAndFlush(params);
    }

    public ModuleInfo findByAppNameAndEnvironment(String appName, String environment) {
        return moduleInfoRepository.findByAppNameAndEnvironment(appName, environment);
    }

    public ModuleInfo findByAppNameAndEnvironmentAndIp(String appName, String environment, String ip) {
        return moduleInfoRepository.findByAppNameAndEnvironmentAndIp(appName, environment, ip);
    }

    public void delete(Long id) {
        moduleInfoRepository.deleteById(id);
    }
}
