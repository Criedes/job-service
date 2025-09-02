package com.ata.it.job.helper;

import com.ata.it.job.dto.JobDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class JobProjectionMapper {
    public Map<String,Object> project(JobDTO d, List<String> fields) {
        Map<String,Object> m = new LinkedHashMap<>();
        for (String f : fields) {
            switch (f) {
                case "id"                       -> m.put("id", d.id());
                case "employer"                 -> m.put("employer", d.employer());
                case "location"                 -> m.put("location", d.location());
                case "job_title"                -> m.put("job_title", d.jobTitle());
                case "years_at_employer"        -> m.put("years_at_employer", d.yearsAtEmployer());
                case "years_of_experience"      -> m.put("years_of_experience", d.yearsOfExperience());
                case "salary"                   -> m.put("salary", d.salary());
                case "signing_bonus"            -> m.put("signing_bonus", d.signingBonus());
                case "annual_bonus"             -> m.put("annual_bonus", d.annualBonus());
                case "annual_stock_value_bonus" -> m.put("annual_stock_value_bonus", d.annualStockValueBonus());
                case "gender"                   -> m.put("gender", d.gender());
                case "timestamp"         -> m.put("timestamp", d.sourceTimestamp());
                case "additional_comments"       -> m.put("additional_comments", d.additionalComments());
                default -> {} // ignore unknowns
            }
        }
        return m;
    }
}
