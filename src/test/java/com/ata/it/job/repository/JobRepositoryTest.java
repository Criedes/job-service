package com.ata.it.job.repository;

import com.ata.it.job.domain.JobEntity;
import com.ata.it.job.repository.specification.JobSpecs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.sql.init.mode=never"
})
class JobRepositoryTest {

    @Autowired
    private JobRepository repo;

    private Long id1, id2, id3;

    @BeforeEach
    void setUp() {
        repo.deleteAll();

        id1 = saveJobEntity("ATA IT", "Bangkok", "Software Engineer",
                "2.0", "5.5", "150000", "10000", "12000", "20000",
                "F", "2025-01-01", "A");

        id2 = saveJobEntity("ATA IT", "Bangkok", "Senior Engineer",
                "3.0", "7.0", "120000", "5000", "8000", "10000",
                "M", "2025-01-02", "B");

        id3 = saveJobEntity("ATA IT", "Bangkok", "Data Scientist",
                "1.0", "3.0", "180000", "15000", "20000", "30000",
                "F", "2025-01-03", "C");

        repo.flush();
    }

    private Long saveJobEntity(
            String employer,
            String location,
            String jobTitle,
            String yearsAtEmployer,
            String yearsOfExperience,
            String salary,
            String signingBonus,
            String annualBonus,
            String annualStockValueBonus,
            String gender,
            String sourceTimestamp,
            String additionalComments
    ) {
        JobEntity e = new JobEntity();
        e.setEmployer(employer);
        e.setLocation(location);
        e.setJobTitle(jobTitle);
        e.setYearsAtEmployer(new BigDecimal(yearsAtEmployer));
        e.setYearsOfExperience(new BigDecimal(yearsOfExperience));
        e.setSalary(new BigDecimal(salary));
        e.setSigningBonus(new BigDecimal(signingBonus));
        e.setAnnualBonus(new BigDecimal(annualBonus));
        e.setAnnualStockValueBonus(new BigDecimal(annualStockValueBonus));
        e.setGender(gender);
        e.setSourceTimestamp(sourceTimestamp);
        e.setAdditionalComments(additionalComments);
        return repo.save(e).getId();
    }


    @Test
    void saveAndFindById() {
        Optional<JobEntity> found = repo.findById(id1);
        assertThat(found).isPresent();
        assertThat(found.get().getJobTitle()).isEqualTo("Software Engineer");
    }

    @Test
    void findAllWithPagingAndSorting() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.desc("salary")));
        Page<JobEntity> page = repo.findAll(pageable);
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent().get(0).getId()).isEqualTo(id3);
        assertThat(page.getContent().get(1).getId()).isEqualTo(id1);
    }

    @Test
    void findAllWithSpecification() {
        var spec = JobSpecs.salaryGte(new BigDecimal("150000"))
                .and(JobSpecs.genderEquals("f"))
                .and(JobSpecs.jobTitleContains("engineer"));
        List<JobEntity> list = repo.findAll(spec);
        assertThat(list).extracting(JobEntity::getId).containsExactly(id1);
    }

    @Test
    void deleteEntity() {
        repo.deleteById(id2);
        assertThat(repo.findById(id2)).isEmpty();
        assertThat(repo.count()).isEqualTo(2);
    }
}
