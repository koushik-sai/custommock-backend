package com.custommock.backend.service;


import java.util.List;

import com.custommock.backend.dto.ReviewResponse;
import com.custommock.backend.dto.SubmitExamRequest;

public interface ReviewService {

    List<ReviewResponse> reviewExam(
            SubmitExamRequest request);

}