package com.ata.it.job.mapper;

import com.ata.it.job.domain.JobEntity;
import com.ata.it.job.dto.JobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface JobMapper {

    @Mapping(source = "jobTitle", target = "jobTitle")
    @Mapping(source = "yearsAtEmployer", target = "yearsAtEmployer")
    @Mapping(source = "yearsOfExperience", target = "yearsOfExperience")
    @Mapping(source = "signingBonus", target = "signingBonus")
    @Mapping(source = "annualBonus", target = "annualBonus")
    @Mapping(source = "annualStockValueBonus", target = "annualStockValueBonus")
    @Mapping(source = "sourceTimestamp", target = "sourceTimestamp")
    @Mapping(source = "additionalComments", target = "additionalComments")
    JobDTO toDto(JobEntity entity);
}
