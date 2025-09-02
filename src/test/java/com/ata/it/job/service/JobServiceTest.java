package com.ata.it.job.service;

import com.ata.it.job.domain.JobEntity;
import com.ata.it.job.dto.JobDTO;
import com.ata.it.job.dto.request.JobQueryParams;
import com.ata.it.job.helper.JobProjectionMapper;
import com.ata.it.job.mapper.JobMapper;
import com.ata.it.job.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock JobRepository repo;
    @Mock JobMapper jobMapper;
    @Mock JobProjectionMapper projectionMapper;

    @InjectMocks JobService service;

    @Test
    void search_noFields_returnsDtoPage_andUsesSortAndPaging() {
        JobQueryParams q = mock(JobQueryParams.class);
        when(q.getSort()).thenReturn("salary");
        when(q.getSortType()).thenReturn("DESC");
        when(q.getPage()).thenReturn(1);
        when(q.getSize()).thenReturn(20);
        when(q.getFields()).thenReturn(null);
        when(q.getSalaryGte()).thenReturn(new BigDecimal("120000"));

        JobEntity e1 = new JobEntity();
        JobEntity e2 = new JobEntity();
        Page<JobEntity> repoPage = new PageImpl<>(
                List.of(e1, e2),
                PageRequest.of(1, 20, Sort.by(Sort.Order.desc("salary"))),
                2
        );

        when(repo.findAll(Mockito.<Specification<JobEntity>>any(), any(Pageable.class)))
                .thenReturn(repoPage);

        JobDTO d1 = mock(JobDTO.class);
        JobDTO d2 = mock(JobDTO.class);
        when(jobMapper.toDto(e1)).thenReturn(d1);
        when(jobMapper.toDto(e2)).thenReturn(d2);

        var result = service.search(q);

        assertThat(result).isNotNull();

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(repo).findAll(any(Specification.class), pageableCaptor.capture());
        Pageable used = pageableCaptor.getValue();
        assertThat(used.getPageNumber()).isEqualTo(1);
        assertThat(used.getPageSize()).isEqualTo(20);
        assertThat(used.getSort().getOrderFor("salary")).isNotNull();
        assertThat(used.getSort().getOrderFor("salary").isDescending()).isTrue();

        verify(jobMapper, times(1)).toDto(e1);
        verify(jobMapper, times(1)).toDto(e2);
        verifyNoInteractions(projectionMapper);
    }

    @Test
    void search_withFields_projectsToMap_andDefaultsSortToJobTitleAsc() {
        JobQueryParams q = mock(JobQueryParams.class);
        when(q.getSort()).thenReturn("unknown");      // triggers default to jobTitle
        when(q.getSortType()).thenReturn("ASC");
        when(q.getPage()).thenReturn(0);
        when(q.getSize()).thenReturn(50);
        when(q.getFields()).thenReturn(List.of("job_title", "gender", "salary"));

        JobEntity e = new JobEntity();
        Page<JobEntity> repoPage = new PageImpl<>(
                List.of(e),
                PageRequest.of(0, 50, Sort.by(Sort.Order.asc("jobTitle"))),
                1
        );

        when(repo.findAll(Mockito.<Specification<JobEntity>>any(), any(Pageable.class)))
                .thenReturn(repoPage);

        JobDTO dto = mock(JobDTO.class);
        when(jobMapper.toDto(e)).thenReturn(dto);

        Map<String, Object> projected = Map.of(
                "job_title", "Software Engineer",
                "gender", "F",
                "salary", new BigDecimal("150000")
        );
        when(projectionMapper.project(eq(dto), eq(List.of("job_title", "gender", "salary"))))
                .thenReturn(projected);

        var result = service.search(q);

        assertThat(result).isNotNull();

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(repo).findAll(any(Specification.class), pageableCaptor.capture());
        Pageable used = pageableCaptor.getValue();
        assertThat(used.getSort().getOrderFor("jobTitle")).isNotNull();
        assertThat(used.getSort().getOrderFor("jobTitle").isAscending()).isTrue();

        verify(projectionMapper).project(dto, List.of("job_title", "gender", "salary"));
    }
}
