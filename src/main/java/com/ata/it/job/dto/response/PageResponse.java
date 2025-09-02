package com.ata.it.job.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;


public record PageResponse<T>(
        List<T> content,
        int number,
        int size,
        @JsonProperty("total_elements")
        long totalElements,
        @JsonProperty("total_pages")
        int totalPages,
        boolean first,
        boolean last
) {
    public static <T> PageResponse<T> of(List<T> content, Page<?> pageMeta) {
        return new PageResponse<>(
                content,
                pageMeta.getNumber(),
                pageMeta.getSize(),
                pageMeta.getTotalElements(),
                pageMeta.getTotalPages(),
                pageMeta.isFirst(),
                pageMeta.isLast()
        );
    }
}
