package com.wooteco.wiki.global.common;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Getter
@Setter
public class PageRequestDto {

    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
    private int pageNumber = 0;

    @Min(value = 1, message = "페이지의 크기는 1 이상이어야 합니다.")
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
