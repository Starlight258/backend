package com.wooteco.wiki.controller;

import static com.wooteco.wiki.exception.ExceptionType.DOCUMENT_DUPLICATE;
import static com.wooteco.wiki.exception.ExceptionType.DOCUMENT_NOT_FOUND;
import static com.wooteco.wiki.exception.ExceptionType.MEMBER_NOT_FOUNT;

import com.wooteco.wiki.annotation.ApiSuccessResponse;
import com.wooteco.wiki.annotation.Auth;
import com.wooteco.wiki.annotation.ErrorApiResponse;
import com.wooteco.wiki.dto.DocumentCreateRequest;
import com.wooteco.wiki.dto.DocumentFindAllByRecentResponse;
import com.wooteco.wiki.dto.DocumentResponse;
import com.wooteco.wiki.dto.DocumentUpdateRequest;
import com.wooteco.wiki.dto.LogDetailResponse;
import com.wooteco.wiki.dto.LogResponse;
import com.wooteco.wiki.service.DocumentSearchService;
import com.wooteco.wiki.service.DocumentService;
import com.wooteco.wiki.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final LogService logService;
    private final DocumentSearchService documentSearchService;

    @PostMapping("")
    @Operation(description = "문서를 작성한다. 로그인 API 호출을 통해 쿠키에 엑세스 토큰이 있어야 한다.")
    @ErrorApiResponse({MEMBER_NOT_FOUNT, DOCUMENT_DUPLICATE})
    @ApiSuccessResponse(bodyType = DocumentResponse.class, body = """
            {
               "documentId": 1,
               "title": "폴라",
               "contents": "폴라 왜 머리 안감아?",
               "writer": "토다리",
               "generateTime": "2024-06-10T17:04:59"
            }
            """)
    public ResponseEntity<DocumentResponse> post(@Auth Long memberId,
                                                 @RequestBody DocumentCreateRequest documentCreateRequest) {
        DocumentResponse response = documentService.post(memberId, documentCreateRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    @Operation(description = "랜덤 문서를 조회 한다.")
    @ErrorApiResponse(DOCUMENT_NOT_FOUND)
    @ApiSuccessResponse(bodyType = DocumentResponse.class, body = """
            {
               "documentId": 1,
               "title": "폴라",
               "contents": "폴라 왜 머리 안감아?",
               "writer": "토다리",
               "generateTime": "2024-06-10T17:04:59"
            }
            """)
    public ResponseEntity<DocumentResponse> getRandom() {
        DocumentResponse response = documentService.getRandom();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{title}")
    @Operation(description = "특정 제목의 문서를 조회한다.")
    @ErrorApiResponse(DOCUMENT_NOT_FOUND)
    @ApiSuccessResponse(bodyType = DocumentResponse.class, body = """
            {
               "documentId": 1,
               "title": "폴라",
               "contents": "폴라 왜 머리 안감아?",
               "writer": "토다리",
               "generateTime": "2024-06-10T17:04:59"
            }
            """)
    public ResponseEntity<DocumentResponse> get(@PathVariable String title) {
        DocumentResponse response = documentService.get(title);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{title}/log")
    @Operation(description = "특정 제목의 문서의 수정 로그를 조회한다.")
    @ApiSuccessResponse(bodyType = LogResponse.class, body = """
            [
                {
                  "logId": 3,
                  "version": 1,
                  "writer": "토다리",
                  "documentBytes": 10,
                  "generateTime": "2024-06-10T17:04:59"
                },
                {
                  "logId": 4,
                  "version": 2,
                  "writer": "폴라",
                  "documentBytes": 10,
                  "generateTime": "2024-06-11T17:04:59"
                }
            ]
            """)
    public ResponseEntity<List<LogResponse>> getLogs(@PathVariable String title) {
        return ResponseEntity.ok(logService.getLogs(title));
    }

    @GetMapping("/log/{logId}")
    @Operation(description = "문서의 수정 로그의 식별자를 통해 수정 로그를 조회한다.")
    @ErrorApiResponse(DOCUMENT_DUPLICATE)
    @ApiSuccessResponse(bodyType = LogDetailResponse.class, body = """
            {
              "logId": 4,
              "title": "폴라",
              "contents": "아니 나 머리 감는다고",
              "writer": "폴라",
              "generateTime": "2024-06-11T17:04:59"
            }
            """)
    public ResponseEntity<LogDetailResponse> getDocumentLogs(@PathVariable Long logId) {
        LogDetailResponse logDetail = logService.getLogDetail(logId);
        return ResponseEntity.ok(logDetail);
    }

    @PutMapping("/{title}")
    @Operation(description = "특정 제목의 문서를 수정한다. 로그인 API 호출을 통해 쿠키에 엑세스 토큰이 있어야 한다.")
    @ErrorApiResponse({MEMBER_NOT_FOUNT, DOCUMENT_NOT_FOUND})
    @ApiSuccessResponse(bodyType = DocumentResponse.class, body = """
            {
              "logId": 4,
              "title": "폴라",
              "contents": "아니 나 머리 감는다고",
              "writer": "폴라",
              "generateTime": "2024-06-11T17:04:59"
            }
            """)
    public ResponseEntity<DocumentResponse> put(@Auth long memberId, @PathVariable String title,
                                                @RequestBody DocumentUpdateRequest documentUpdateRequest) {
        DocumentResponse response = documentService.put(memberId, title, documentUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent")
    @Operation(description = "최근 변경된 순서로 문서 목록을 조회한다.")
    @ApiSuccessResponse(bodyType = DocumentFindAllByRecentResponse.class, body = """
            {
              "documents": [
                {
                  "documentId": 10,
                  "title": "ㅇㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇ",
                  "generateTime": "2024-06-10T14:53:14.596248"
                },
                {
                  "documentId": 1,
                  "title": "폴라바",
                  "generateTime": "2024-06-10T01:26:24.108406"
                },
                {
                  "documentId": 9,
                  "title": "aaaaaaaaaaa",
                  "generateTime": "2024-06-10T01:25:29.993873"
                },
                {
                  "documentId": 8,
                  "title": "title_fa5fd9a1a7c3",
                  "generateTime": "2024-06-10T01:24:16.252505"
                },
                {
                  "documentId": 7,
                  "title": "폴라포ssss",
                  "generateTime": "2024-04-03T22:50:55.05836"
                },
                {
                  "documentId": 6,
                  "title": "폴라포ss",
                  "generateTime": "2024-04-03T14:35:45.713133"
                },
                {
                  "documentId": 5,
                  "title": "폴라포",
                  "generateTime": "2024-03-22T16:20:24.63458"
                }
              ]
            }
            """)
    public ResponseEntity<DocumentFindAllByRecentResponse> getRecentDocuments() {
        DocumentFindAllByRecentResponse response = documentService.getRecentDocuments();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(description = "문서의 제목을 이용해 검색한다.")
    @ApiResponse(responseCode = "200", description = "요청이 정상적으로 처리된 경우", content = @Content(schema = @Schema(implementation = String[].class), examples = @ExampleObject("""
            [
              "폴라바",
              "폴라포",
              "폴라포ss",
              "폴라포ssss"
            ]
            """)))
    public List<String> search(@RequestParam String keyWord) {
        return documentSearchService.search(keyWord);
    }
}
