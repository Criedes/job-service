package com.ata.it.job.mapper;

import com.ata.it.job.dto.request.JobQueryParams;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JobQueryParamsMapperTest {

    private final JobQueryParamsMapper mapper = Mappers.getMapper(JobQueryParamsMapper.class);

    @Test
    void toDto_mapsAllFieldsCorrectly() {
        // Arrange
        String jobTitle = " Engineer ";
        String gender = " M ";
        BigDecimal salaryGte = new BigDecimal("100000");
        BigDecimal salaryLte = new BigDecimal("200000");
        BigDecimal salaryGt = new BigDecimal("120000");
        BigDecimal salaryLt = new BigDecimal("180000");
        String sort = " salary ";
        String sortType = " DESC ";
        int page = 2;
        int size = 25;
        String fieldsCsv = "job_title,gender,salary";

        // Act
        JobQueryParams params = mapper.toDto(
                jobTitle,
                gender,
                salaryGte,
                salaryLte,
                salaryGt,
                salaryLt,
                sort,
                sortType,
                page,
                size,
                fieldsCsv
        );

        // Assert
        assertThat(params).isNotNull();
        assertThat(params.getJobTitle()).isEqualTo("Engineer"); // trimmed
        assertThat(params.getGender()).isEqualTo("M");
        assertThat(params.getSalaryGte()).isEqualByComparingTo("100000");
        assertThat(params.getSalaryLte()).isEqualByComparingTo("200000");
        assertThat(params.getSalaryGt()).isEqualByComparingTo("120000");
        assertThat(params.getSalaryLt()).isEqualByComparingTo("180000");
        assertThat(params.getSort()).isEqualTo("salary"); // trimmed
        assertThat(params.getSortType()).isEqualTo("DESC");
        assertThat(params.getPage()).isEqualTo(2);
        assertThat(params.getSize()).isEqualTo(25);
        assertThat(params.getFields()).containsExactly("job_title", "gender", "salary");
    }

    @Test
    void toDto_handlesNullAndEmptyValues() {
        JobQueryParams params = mapper.toDto(
                null,
                "   ",  // whitespace only
                null, null, null, null,
                "", "   ",
                null, null,
                "  , , "
        );

        assertThat(params.getJobTitle()).isNull();
        assertThat(params.getGender()).isNull();
        assertThat(params.getSort()).isNull();
        assertThat(params.getSortType()).isNull();
        assertThat(params.getFields()).isEmpty();
    }

    @Test
    void trimToNull_worksAsExpected() {
        assertThat(JobQueryParamsMapper.trimToNull(" test ")).isEqualTo("test");
        assertThat(JobQueryParamsMapper.trimToNull("   ")).isNull();
        assertThat(JobQueryParamsMapper.trimToNull(null)).isNull();
    }

    @Test
    void csvToList_worksAsExpected() {
        assertThat(JobQueryParamsMapper.csvToList("a, b , ,c"))
                .containsExactly("a", "b", "c");
        assertThat(JobQueryParamsMapper.csvToList("   ")).isNull();
        assertThat(JobQueryParamsMapper.csvToList(null)).isNull();
    }
}
