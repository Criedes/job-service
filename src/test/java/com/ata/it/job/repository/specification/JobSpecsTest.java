package com.ata.it.job.repository.specification;

import com.ata.it.job.domain.JobEntity;
import com.ata.it.job.dto.request.JobQueryParams;
import com.ata.it.job.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {
                "spring.flyway.enabled=false",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                // make sure no SQL initializer competes with JPA
                "spring.sql.init.mode=never"
        }
)
class JobSpecsTest {

    @Autowired
    private JobRepository repo;

    private Long engF150kId;
    private Long engM120kId;
    private Long dsF180kId;

    @BeforeEach
    void seed() {
        repo.deleteAll();

        engF150kId = saveJobEntity(
                "ATA IT", "Bangkok", "Software Engineer",
                new BigDecimal("2.0"), new BigDecimal("5.5"),
                new BigDecimal("150000"), new BigDecimal("10000"),
                new BigDecimal("12000"), new BigDecimal("20000"),
                "F", "2025-01-01", "A"
        );

        engM120kId = saveJobEntity(
                "ATA IT", "Bangkok", "Senior Engineer",
                new BigDecimal("3.0"), new BigDecimal("7.0"),
                new BigDecimal("120000"), new BigDecimal("5000"),
                new BigDecimal("8000"), new BigDecimal("10000"),
                "M", "2025-01-02", "B"
        );

        dsF180kId = saveJobEntity(
                "ATA IT", "Bangkok", "Data Scientist",
                new BigDecimal("1.0"), new BigDecimal("3.0"),
                new BigDecimal("180000"), new BigDecimal("15000"),
                new BigDecimal("20000"), new BigDecimal("30000"),
                "F", "2025-01-03", "C"
        );

        repo.flush();
    }

    private Long saveJobEntity(
            String employer,
            String location,
            String jobTitle,
            BigDecimal yearsAtEmployer,
            BigDecimal yearsOfExperience,
            BigDecimal salary,
            BigDecimal signingBonus,
            BigDecimal annualBonus,
            BigDecimal annualStockValueBonus,
            String gender,
            String sourceTimestamp,
            String additionalComments
    ) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setEmployer(employer);
        jobEntity.setLocation(location);
        jobEntity.setJobTitle(jobTitle);
        jobEntity.setYearsAtEmployer(yearsAtEmployer);
        jobEntity.setYearsOfExperience(yearsOfExperience);
        jobEntity.setSalary(salary);
        jobEntity.setSigningBonus(signingBonus);
        jobEntity.setAnnualBonus(annualBonus);
        jobEntity.setAnnualStockValueBonus(annualStockValueBonus);
        jobEntity.setGender(gender);
        jobEntity.setSourceTimestamp(sourceTimestamp);
        jobEntity.setAdditionalComments(additionalComments);
        return repo.save(jobEntity).getId();
    }


    @Test
    void jobTitleContains_matchesCaseInsensitiveSubstring() {
        var spec = JobSpecs.jobTitleContains("engineer");
        List<JobEntity> found = repo.findAll(spec);
        assertThat(found).extracting(JobEntity::getId)
                .containsExactlyInAnyOrder(engF150kId, engM120kId);
    }

    @Test
    void genderEquals_matchesCaseInsensitive() {
        var spec = JobSpecs.genderEquals("f");
        List<JobEntity> found = repo.findAll(spec);
        assertThat(found).extracting(JobEntity::getId)
                .containsExactlyInAnyOrder(engF150kId, dsF180kId);
    }

    @Test
    void salaryGte_filtersProperly() {
        var spec = JobSpecs.salaryGte(new BigDecimal("150000"));
        List<JobEntity> found = repo.findAll(spec);
        assertThat(found).extracting(JobEntity::getId)
                .containsExactlyInAnyOrder(engF150kId, dsF180kId);
    }

    @Test
    void salaryLt_filtersProperly() {
        var spec = JobSpecs.salaryLt(new BigDecimal("130000"));
        List<JobEntity> found = repo.findAll(spec);
        assertThat(found).extracting(JobEntity::getId)
                .containsExactly(engM120kId);
    }

    @Test
    void all_combinesOnlyNonEmptyPredicates() {
        // job_title contains 'engineer' AND gender = 'F' AND salary >= 140000
        JobQueryParams q = new JobQueryParams();
        // Assuming Lombok @Data on JobQueryParams with snake_case accessors:
        q.setJobTitle("engineer");
        q.setGender("F");
        q.setSalaryGte(new BigDecimal("140000"));
        // leave others null

        var spec = JobSpecs.all(q);
        List<JobEntity> found = repo.findAll(spec);
        assertThat(found).extracting(JobEntity::getId)
                .containsExactly(engF150kId);
    }

    @Test
    void all_withAllNulls_returnsEverything() {
        JobQueryParams q = new JobQueryParams(); // all fields null/empty
        var spec = JobSpecs.all(q);
        List<JobEntity> found = repo.findAll(spec);
        assertThat(found).hasSize(3);
    }
}
