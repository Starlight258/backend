package com.wooteco.wiki.organizationdocument.controller;

import com.wooteco.wiki.global.common.ApiResponse;
import com.wooteco.wiki.global.common.ApiResponse.SuccessBody;
import com.wooteco.wiki.global.common.ApiResponseGenerator;
import com.wooteco.wiki.organizationdocument.dto.request.OrganizationDocumentUpdateRequest;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.service.OrganizationDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/organization")
@RestController
public class OrganizationDocumentController {

    private final OrganizationDocumentService organizationDocumentService;

    @Operation(summary = "조직 위키 글 수정", description = "조직 위키 글을 수정합니다.")
    @PutMapping
    public ApiResponse<SuccessBody<OrganizationDocumentResponse>> modifyOrganizationDocumentContents(
            @RequestBody OrganizationDocumentUpdateRequest organizationDocumentModifyContentsRequest) {
        OrganizationDocumentResponse organizationDocumentResponse = organizationDocumentService.update(
                organizationDocumentModifyContentsRequest);

        return ApiResponseGenerator.success(organizationDocumentResponse);
    }
}
