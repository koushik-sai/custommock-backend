package com.custommock.backend.service;


import com.custommock.backend.dto.ResultResponse;
import com.custommock.backend.dto.SubmitExamRequest;

public interface ResultService {

    ResultResponse submitExam(
            SubmitExamRequest request);

}