package com.wooteco.wiki.admin.controller;

import com.wooteco.wiki.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<Void> deleteDocumentByDocumentId(@PathVariable Long documentId) {
        adminService.deleteDocumentByDocumentId(documentId);
        return ResponseEntity.noContent().build();
    }
}
