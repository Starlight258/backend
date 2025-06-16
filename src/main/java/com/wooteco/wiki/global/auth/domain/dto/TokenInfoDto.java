package com.wooteco.wiki.global.auth.domain.dto;

import com.wooteco.wiki.admin.domain.Admin;
import com.wooteco.wiki.global.auth.Role;

public record TokenInfoDto (Long id, Role role) {

    public static TokenInfoDto of(Admin admin, Role role) {
        return new TokenInfoDto(admin.getId(), role);
    }
}
