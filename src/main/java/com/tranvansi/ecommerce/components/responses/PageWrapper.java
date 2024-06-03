package com.tranvansi.ecommerce.components.responses;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class PageWrapper<T> {

    @JsonProperty("content")
    private List<T> content;

    @JsonProperty("number")
    private int number;

    @JsonProperty("size")
    private int size;

    @JsonProperty("totalElements")
    private long totalElements;

    public static <T> PageWrapper<T> of(Page<T> page) {
        return new PageWrapper<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
    }

    public Page<T> toPage() {
        Pageable pageable = PageRequest.of(number, size);
        return new PageImpl<>(content, pageable, totalElements);
    }
}
