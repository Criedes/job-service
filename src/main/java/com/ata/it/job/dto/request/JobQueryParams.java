package com.ata.it.job.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JobQueryParams {
    private String jobTitle;
    private String gender;

    private BigDecimal salaryGte;
    private BigDecimal salaryLte;
    private BigDecimal salaryGt;
    private BigDecimal salaryLt;

    private String sort;
    private String sortType;

    private Integer page;
    private Integer size;

    private List<String> fields;
}

