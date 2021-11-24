package com.alibaba.repeater.console.dal.dao;

import com.alibaba.repeater.console.common.params.BaseParams;
import com.alibaba.repeater.console.dal.model.Replay;
import com.alibaba.repeater.console.dal.repository.ReplayRepository;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * {@link ReplayDao}
 * <p>
 *
 * @author zhaoyb1990
 */
@Component("replayDao")
public class ReplayDao {

    @Resource
    private ReplayRepository replayRepository;

    public Replay save(Replay replay) {
        return replayRepository.save(replay);
    }

    public Replay saveAndFlush(Replay replay) {
        return replayRepository.saveAndFlush(replay);
    }

    public Replay findByRepeatId(String repeatId) {
        return replayRepository.findByRepeatId(repeatId);
    }

    public Page<Replay> selectByParams(BaseParams params) {
        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getSize(), Sort.Direction.DESC, "id");
        final Page<Replay> all = replayRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = Lists.newArrayList();
                    if (params.getAppName() != null && !params.getAppName().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("appName"), params.getAppName()));
                    }
                    if (params.getTraceId() != null && !params.getTraceId().isEmpty()) {
                        predicates.add(cb.equal(root.<String>get("traceId"), params.getTraceId()));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                },
                pageable
        );
        return all;
    }
}
