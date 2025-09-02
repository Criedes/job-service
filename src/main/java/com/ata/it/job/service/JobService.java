package com.ata.it.job.service;

import com.ata.it.job.domain.JobEntity;
import com.ata.it.job.dto.JobDTO;
import com.ata.it.job.dto.response.PageResponse;
import com.ata.it.job.helper.JobProjectionMapper;
import com.ata.it.job.mapper.JobMapper;
import com.ata.it.job.repository.JobRepository;
import com.ata.it.job.repository.specification.JobSpecs;
import com.ata.it.job.dto.request.JobQueryParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository repo;
    private final JobMapper jobMapper;
    private final JobProjectionMapper projectionMapper;

    public PageResponse<?> search(JobQueryParams q) {
        log.info("JobService: Start job search with parameters {}", q);
        Specification<JobEntity> spec = JobSpecs.all(q);
        Sort sort = sortOf(q.getSort(), q.getSortType());
        Pageable pageable = PageRequest.of(
                q.getPage() == null ? 0 : Math.max(q.getPage(), 0),
                q.getSize() == null ? 50 : Math.max(q.getSize(), 1),
                sort
        );

        Page<JobEntity> page = repo.findAll(spec, pageable);

        // Map to DTOs first (never return entities)
        Page<JobDTO> dtoPage = page.map(jobMapper::toDto);

        // If sparse fields requested, project each DTO to Map<String,Object>
        if (q.getFields() != null && !q.getFields().isEmpty()) {
            log.info("JobService: fields projection: {}",  q.getFields());
            List<Map<String,Object>> content = dtoPage.getContent().stream()
                    .map(d -> projectionMapper.project(d, q.getFields()))
                    .toList();
            return PageResponse.of(content, dtoPage); // stable page wrapper
        }

        log.info("JobService: End job search");
        return PageResponse.of(dtoPage.getContent(), dtoPage);
    }

    private Sort sortOf(String sortField, String sortType) {
        String attr = switch (sortField == null ? "" : sortField) {
            case "salary"    -> "salary";
            case "gender"    -> "gender";
            case "employer"  -> "employer";
            case "location"  -> "location";
            default          -> "jobTitle";
        };
        Sort.Direction dir = "DESC".equalsIgnoreCase(sortType) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(new Sort.Order(dir, attr));
    }
}