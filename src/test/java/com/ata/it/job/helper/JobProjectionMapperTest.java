package com.ata.it.job.helper;

import com.ata.it.job.dto.JobDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JobProjectionMapperTest {

    private JobProjectionMapper mapper;
    private JobDTO dto;

    @BeforeEach
    void setUp() {
        mapper = new JobProjectionMapper();

        dto = new JobDTO(
                1L,
                "Allianz",
                "Bangkok",
                "Engineer",
                new BigDecimal("2.5"),
                new BigDecimal("5.0"),
                new BigDecimal("120000"),
                new BigDecimal("10000"),
                new BigDecimal("15000"),
                new BigDecimal("20000"),
                "F",
                "2025-09-01T12:00:00Z",
                "Great place to work"
        );
    }

    @Test
    void project_includesRequestedFieldsOnly() {
        List<String> fields = List.of("id", "employer", "job_title", "salary");

        Map<String, Object> result = mapper.project(dto, fields);

        assertThat(result).containsEntry("id", 1L);
        assertThat(result).containsEntry("employer", "Allianz");
        assertThat(result).containsEntry("job_title", "Engineer");
        assertThat(result).containsEntry("salary", new BigDecimal("120000"));

        assertThat(result).doesNotContainKeys("gender", "location", "additional_comments");
    }

    @Test
    void project_handlesAllFields() {
        List<String> allFields = List.of(
                "id", "employer", "location", "job_title",
                "years_at_employer", "years_of_experience",
                "salary", "signing_bonus", "annual_bonus",
                "annual_stock_value_bonus", "gender",
                "source_timestamp", "additional_comments"
        );

        Map<String, Object> result = mapper.project(dto, allFields);

        assertThat(result).hasSize(allFields.size());
        assertThat(result).containsEntry("location", "Bangkok");
        assertThat(result).containsEntry("gender", "F");
        assertThat(result).containsEntry("additional_comments", "Great place to work");
    }

    @Test
    void project_ignoresUnknownFields() {
        List<String> fields = List.of("id", "unknown_field");

        Map<String, Object> result = mapper.project(dto, fields);

        assertThat(result).containsOnlyKeys("id");
    }
}
