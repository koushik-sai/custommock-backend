package com.custommock.backend.controller;


import org.springframework.web.bind.annotation.*;

import com.custommock.backend.dto.AnswerKeyRequest;
import com.custommock.backend.dto.AnswerKeyResponse;
import com.custommock.backend.service.AnswerKeyService;

@RestController
@RequestMapping("/api/admin/answer-keys")
@CrossOrigin("*")
public class AdminAnswerKeyController {

    private final AnswerKeyService answerKeyService;

    public AdminAnswerKeyController(
            AnswerKeyService answerKeyService) {

        this.answerKeyService = answerKeyService;
    }

    @PostMapping
    public AnswerKeyResponse addAnswerKey(
            @RequestBody AnswerKeyRequest request) {

        return answerKeyService
                .addAnswerKey(request);
    }

}