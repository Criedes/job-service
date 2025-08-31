package com.ata.it.job.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class JobQueryParams {
    private String job_title;
    private String gender;

    private BigDecimal salary_gte;
    private BigDecimal salary_lte;
    private BigDecimal salary_gt;
    private BigDecimal salary_lt;

    private String sort;
    private String sort_type;

    private Integer page;
    private Integer size;

    private List<String> fields;
}

