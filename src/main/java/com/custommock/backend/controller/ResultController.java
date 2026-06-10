package com.custommock.backend.controller;


import org.springframework.web.bind.annotation.*;

import com.custommock.backend.dto.ResultResponse;
import com.custommock.backend.dto.SubmitExamRequest;
import com.custommock.backend.service.ResultService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin("*")
public class ResultController {

    private final ResultService resultService;

    public ResultController(
            ResultService resultService) {

        this.resultService = resultService;
    }

    @PostMapping("/submit")
    public ResultResponse submitExam(

            @RequestBody
            SubmitExamRequest request) {

        return resultService
                .submitExam(request);

    }

}