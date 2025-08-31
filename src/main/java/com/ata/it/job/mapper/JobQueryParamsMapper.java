package com.ata.it.job.mapper;

import com.ata.it.job.dto.request.JobQueryParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Mapper()
public interface JobQueryParamsMapper {

    @Mapping(target = "salary_lte", source = "salaryLte")
    @Mapping(target = "salary_lt", source = "salaryLt")
    @Mapping(target = "salary_gte", source = "salaryGte")
    @Mapping(target = "salary_gt", source = "salaryGt")
    @Mapping(target = "job_title", source = "jobTitle", qualifiedByName = "trimToNull")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "trimToNull")
    @Mapping(target = "sort", source = "sort", qualifiedByName = "trimToNull")
    @Mapping(target = "sort_type", source = "sortType", qualifiedByName = "trimToNull")
    @Mapping(target = "fields", source = "fieldsCsv", qualifiedByName = "csvToList")
    JobQueryParams toDto(
            String jobTitle,
            String gender,
            BigDecimal salaryGte,
            BigDecimal salaryLte,
            BigDecimal salaryGt,
            BigDecimal salaryLt,
            String sort,
            String sortType,
            Integer page,
            Integer size,
            String fieldsCsv
    );

    @Named("trimToNull")
    static String trimToNull(String value) {
        if (value == null) return null;
        String t = value.trim();
        return t.isEmpty() ? null : t;
    }

    @Named("csvToList")
    static List<String> csvToList(String csv) {
        if (csv == null || csv.trim().isEmpty()) return null;
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
