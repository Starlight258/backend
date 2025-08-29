package com.wooteco.wiki.document.controller;

import com.wooteco.wiki.global.common.ApiResponse;
import com.wooteco.wiki.global.common.ApiResponse.SuccessBody;
import com.wooteco.wiki.global.common.ApiResponseGenerator;
import com.wooteco.wiki.global.common.ResponseDto;
import com.wooteco.wiki.organizationevent.dto.OrganizationEventCreateRequest;
import com.wooteco.wiki.organizationevent.dto.OrganizationEventCreateResponse;
import com.wooteco.wiki.organizationevent.service.OrganizationEventService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization-events")
public class OrganizationEventController {

    private final OrganizationEventService organizationEventService;

    public OrganizationEventController(final OrganizationEventService organizationEventService) {
        this.organizationEventService = organizationEventService;
    }

    @Operation(summary = "조직 이벤트 생성", description = "조직 이벤트를 생성합니다.")
    @PostMapping("")
    public ApiResponse<SuccessBody<OrganizationEventCreateResponse>> post(
            @RequestBody OrganizationEventCreateRequest organizationEventCreateRequest) {
        OrganizationEventCreateResponse response = organizationEventService.post(organizationEventCreateRequest);
        return ApiResponseGenerator.success(response, HttpStatus.CREATED);
    }

    private <T> ResponseDto<List<T>> convertToResponse(Page<T> pageResponses) {
        return ResponseDto.of(
                pageResponses.getNumber(),
                pageResponses.getTotalPages(),
                pageResponses.getContent()
        );
    }
}
