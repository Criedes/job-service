package com.ata.it.job.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_data")
public class JobEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employer;
    private String location;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "years_at_employer")
    private BigDecimal yearsAtEmployer;

    @Column(name = "years_of_experience")
    private BigDecimal yearsOfExperience;

    private BigDecimal salary;

    @Column(name = "signing_bonus")
    private BigDecimal signingBonus;

    @Column(name = "annual_bonus")
    private BigDecimal annualBonus;

    @Column(name = "annual_stock_value_bonus")
    private BigDecimal annualStockValueBonus;

    private String gender;

    @Column(name = "source_timestamp")
    private String sourceTimestamp;

    @Lob
    @Column(name = "additional_comments")
    private String additionalComments;

}
