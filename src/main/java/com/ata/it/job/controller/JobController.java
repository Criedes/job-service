package com.ata.it.job.controller;

import com.ata.it.job.dto.response.PageResponse;
import com.ata.it.job.mapper.JobQueryParamsMapper;
import com.ata.it.job.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService service;
    private final JobQueryParamsMapper jobQueryParamsMapper;

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(name = "job_title", required = false) String jobTitle,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "salary_gte", required = false) BigDecimal salaryGte,
            @RequestParam(name = "salary_lte", required = false) BigDecimal salaryLte,
            @RequestParam(name = "salary_gt", required = false) BigDecimal salaryGt,
            @RequestParam(name = "salary_lt", required = false) BigDecimal salaryLt,
            @RequestParam(name = "fields", required = false) String fieldsCsv,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "sort_type", required = false, defaultValue = "ASC") String sortType,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "50") Integer size
    ) {
        val jobQueryParams = jobQueryParamsMapper.toDto(
                jobTitle, gender,
                salaryGte, salaryLte, salaryGt, salaryLt,
                sort, sortType,
                page, size,
                fieldsCsv
        );

        PageResponse<?> pageResult = service.search(jobQueryParams);

        return ResponseEntity.ok(pageResult);

    }
}
