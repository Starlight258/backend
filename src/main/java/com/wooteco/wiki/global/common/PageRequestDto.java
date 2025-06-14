package com.wooteco.wiki.global.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Getter
@Setter
public class PageRequestDto {

    private int pageNumber = 0;
    private int pageSize = 10;
    private String sort = "id";
    private String sortDirection = "ASC";

    public Pageable toPageable() {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        return PageRequest.of(pageNumber, pageSize, direction, sort);
    }

    public Pageable toPageableUnsorted() {
        return PageRequest.of(pageNumber, pageSize, Sort.unsorted());
    }
}  





