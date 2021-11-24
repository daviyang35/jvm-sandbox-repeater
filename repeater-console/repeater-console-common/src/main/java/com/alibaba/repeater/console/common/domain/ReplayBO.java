package com.alibaba.repeater.console.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link ReplayBO}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class ReplayBO extends BaseBO {

    private Long id;

    private LocalDateTime gmtCreate;

    private String appName;

    private String ip;

    private String environment;

    private String repeatId;

    private String traceId;

    private String response;

    private Boolean success;

    private Long cost;

    private ReplayStatus status;

    private RecordDetailBO record;

    private List<MockInvocationBO> mockInvocations;

    private List<DifferenceBO> differences;

    @Override
    public String toString() {
        return super.toString();
    }
}
