package com.adplatform.restApi.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageDto<T> {

    private int totalPage;

    private int currentPage;

    private Long totalCount;

    private int currentCount;

    @JsonProperty("isFirst")
    private boolean first;

    @JsonProperty("isLast")
    private boolean last;

    @JsonProperty("hasNext")
    @Accessors(fluent = true)
    private boolean hasNext;

    @JsonProperty("isEmpty")
    private boolean empty;

    private Sort sort;

    private List<T> content;

    public static <T> PageDto<T> create(Page<T> page, List<T> content) {
        return PageDto.<T>builder()
                .totalPage(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .totalCount(page.getTotalElements())
                .currentCount(page.getNumberOfElements())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .empty(page.isEmpty())
                .sort(page.getSort())
                .content(content)
                .build();
    }

    public static <T> PageDto<T> create(Page<T> page) {
        return PageDto.<T>builder()
                .totalPage(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .totalCount(page.getTotalElements())
                .currentCount(page.getNumberOfElements())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .empty(page.isEmpty())
                .sort(page.getSort())
                .content(page.getContent())
                .build();
    }
}
