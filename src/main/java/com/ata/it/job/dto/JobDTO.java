package com.ata.it.job.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record JobDTO(
        Long id,
        String employer,
        String location,
        @JsonProperty("job_title")
        String jobTitle,
        @JsonProperty("years_at_employer")
        BigDecimal yearsAtEmployer,
        @JsonProperty("years_of_experience")
        BigDecimal yearsOfExperience,
        @JsonProperty("salary")
        BigDecimal salary,
        @JsonProperty("signing_bonus")
        BigDecimal signingBonus,
        @JsonProperty("annual_bonus")
        BigDecimal annualBonus,
        @JsonProperty("annual_stock_value_bonus")
        BigDecimal annualStockValueBonus,
        @JsonProperty("gender")
        String gender,
        @JsonProperty("timestamp")
        String sourceTimestamp,
        @JsonProperty("additional_comments")
        String additionalComments
) {}
