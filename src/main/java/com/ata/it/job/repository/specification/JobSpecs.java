package com.ata.it.job.repository.specification;

import com.ata.it.job.domain.JobEntity;
import com.ata.it.job.dto.request.JobQueryParams;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JobSpecs {

    public static Specification<JobEntity> jobTitleContains(String q) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(q)) return cb.conjunction();
            return cb.like(cb.lower(root.get("jobTitle")), "%" + q.toLowerCase() + "%");
        };
    }

    public static Specification<JobEntity> genderEquals(String g) {
        return (root, query, cb) -> {
            if (ObjectUtils.isEmpty(g)) return cb.conjunction();
            return cb.equal(cb.lower(root.get("gender")), g.toLowerCase());
        };
    }

    public static Specification<JobEntity> salaryGte(BigDecimal v) {
        return (root, query, cb) ->
                ObjectUtils.isEmpty(v) ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("salary"), v);
    }

    public static Specification<JobEntity> salaryLte(BigDecimal v) {
        return (root, query, cb) ->
                ObjectUtils.isEmpty(v) ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("salary"), v);
    }

    public static Specification<JobEntity> salaryGt(BigDecimal v) {
        return (root, query, cb) ->
                ObjectUtils.isEmpty(v) ? cb.conjunction() : cb.greaterThan(root.get("salary"), v);
    }

    public static Specification<JobEntity> salaryLt(BigDecimal v) {
        return (root, query, cb) ->
                ObjectUtils.isEmpty(v) ? cb.conjunction() : cb.lessThan(root.get("salary"), v);
    }

    public static Specification<JobEntity> all(JobQueryParams q) {
        return Specification.allOf(Arrays.asList(
                ObjectUtils.isEmpty(q.getJobTitle()) ? null : jobTitleContains(q.getJobTitle()),
                ObjectUtils.isEmpty(q.getGender()) ? null : genderEquals(q.getGender()),
                ObjectUtils.isEmpty(q.getSalaryGte()) ? null : salaryGte(q.getSalaryGte()),
                ObjectUtils.isEmpty(q.getSalaryLte()) ? null : salaryLte(q.getSalaryLte()),
                ObjectUtils.isEmpty(q.getSalaryGt()) ? null : salaryGt(q.getSalaryGt()),
                ObjectUtils.isEmpty(q.getSalaryLt()) ? null : salaryLt(q.getSalaryLt())
        ));
    }
}