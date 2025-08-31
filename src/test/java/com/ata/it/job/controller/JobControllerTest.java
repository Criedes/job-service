package com.ata.it.job.controller;

import com.ata.it.job.dto.JobDTO;
import com.ata.it.job.dto.request.JobQueryParams;
import com.ata.it.job.dto.response.PageResponse;
import com.ata.it.job.mapper.JobQueryParamsMapper;
import com.ata.it.job.service.JobService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @Mock JobService service;
    @Mock JobQueryParamsMapper jobQueryParamsMapper;
    @InjectMocks JobController controller;

    @Test
    void list_returnsDtoPage() {
        var jobQueryParams = new JobQueryParams();
        when(jobQueryParamsMapper.toDto(
                "engineer","F", new BigDecimal("120000"),null,null,null,
                "salary","DESC",0,50,null
        )).thenReturn(jobQueryParams);

        var dto = new JobDTO(1L,"ACME","BKK","Software Engineer",
                null,null,new BigDecimal("150000"),
                null,null,null,"F","2024-01-01","note");

        PageResponse<?> serviceResult = new PageResponse<>(
                List.of(dto), 0, 50, 1, 1, true, true
        );
        doReturn(serviceResult).when(service).search(jobQueryParams);

        ResponseEntity<?> resp = controller.list(
                "engineer","F", new BigDecimal("120000"),null,
                null,null,null,"salary","DESC",0,50
        );

        assertThat(resp.getBody()).isEqualTo(serviceResult);
        verify(service).search(jobQueryParams);
    }

    @Test
    void list_returnsSparseProjectionPage() {
        var jobQueryParams = new JobQueryParams();
        when(jobQueryParamsMapper.toDto(
                "engineer","F", null,null,null,null,
                "salary","DESC",0,50,"job_title,gender,salary"
        )).thenReturn(jobQueryParams);

        var row = Map.of(
                "job_title","Software Engineer",
                "gender","F",
                "salary", new BigDecimal("150000")
        );

        PageResponse<?> serviceResult = new PageResponse<>(
                List.of(row), 0, 50, 1, 1, true, true
        );
        doReturn(serviceResult).when(service).search(jobQueryParams);

        ResponseEntity<?> resp = controller.list(
                "engineer","F", null,null,
                null,null,"job_title,gender,salary",
                "salary","DESC",0,50
        );

        assertThat(resp.getBody()).isEqualTo(serviceResult);
        verify(service).search(jobQueryParams);
    }
}
