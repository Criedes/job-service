package com.ata.it.job.mapper;

import com.ata.it.job.domain.JobEntity;
import com.ata.it.job.dto.JobDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JobMapperTest {

    private final JobMapper mapper = Mappers.getMapper(JobMapper.class);

    @Test
    void toDto_mapsAllFields() {
        // Arrange: build a JobEntity with all fields populated
        JobEntity jobEntity = getJobEntity();

        // Act
        JobDTO dto = mapper.toDto(jobEntity);

        // Assert
        assertThat(dto).isNotNull();
        // record-style accessors (adjust to your DTO getters if it's not a record)
        assertThat(dto.id()).isEqualTo(42L);
        assertThat(dto.employer()).isEqualTo("ATA IT");
        assertThat(dto.location()).isEqualTo("Bangkok");
        assertThat(dto.jobTitle()).isEqualTo("Software Engineer");
        assertThat(dto.yearsAtEmployer()).isEqualByComparingTo("2.50");
        assertThat(dto.yearsOfExperience()).isEqualByComparingTo("5.75");
        assertThat(dto.salary()).isEqualByComparingTo("120000");
        assertThat(dto.signingBonus()).isEqualByComparingTo("10000");
        assertThat(dto.annualBonus()).isEqualByComparingTo("15000");
        assertThat(dto.annualStockValueBonus()).isEqualByComparingTo("20000");
        assertThat(dto.gender()).isEqualTo("F");
        assertThat(dto.sourceTimestamp()).isEqualTo("2025-09-01T12:00:00Z");
        assertThat(dto.additionalComments()).isEqualTo("Great team!");
    }

    private static JobEntity getJobEntity() {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setId(42L);
        jobEntity.setEmployer("ATA IT");
        jobEntity.setLocation("Bangkok");
        jobEntity.setJobTitle("Software Engineer");
        jobEntity.setYearsAtEmployer(new BigDecimal("2.50"));
        jobEntity.setYearsOfExperience(new BigDecimal("5.75"));
        jobEntity.setSalary(new BigDecimal("120000"));
        jobEntity.setSigningBonus(new BigDecimal("10000"));
        jobEntity.setAnnualBonus(new BigDecimal("15000"));
        jobEntity.setAnnualStockValueBonus(new BigDecimal("20000"));
        jobEntity.setGender("F");
        jobEntity.setSourceTimestamp("2025-09-01T12:00:00Z");
        jobEntity.setAdditionalComments("Great team!");
        return jobEntity;
    }

    @Test
    void toDto_nullInput_returnsNull() {
        assertThat(mapper.toDto(null)).isNull();
    }
}
