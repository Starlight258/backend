package com.wooteco.wiki.dto;

import com.wooteco.wiki.domain.Admin;

public record TokenInfoDto (Long id) {

    public static TokenInfoDto of(Admin admin) {
        return new TokenInfoDto(admin.getId());
    }
}
