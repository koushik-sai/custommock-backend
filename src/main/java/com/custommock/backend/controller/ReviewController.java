package com.custommock.backend.controller;


import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.custommock.backend.dto.ReviewResponse;
import com.custommock.backend.dto.SubmitExamRequest;
import com.custommock.backend.service.ReviewService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(
            ReviewService reviewService) {

        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    public List<ReviewResponse>
            reviewExam(

            @RequestBody
            SubmitExamRequest request) {

        return reviewService
                .reviewExam(request);

    }

}