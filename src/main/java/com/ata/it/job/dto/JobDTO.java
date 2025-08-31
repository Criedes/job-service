package com.ata.it.job.dto;

import java.math.BigDecimal;

public record JobDTO(
        Long id,
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
) {}
