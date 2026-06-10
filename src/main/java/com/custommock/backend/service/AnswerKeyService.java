package com.custommock.backend.service;



import com.custommock.backend.dto.AnswerKeyRequest;
import com.custommock.backend.dto.AnswerKeyResponse;

public interface AnswerKeyService {

    AnswerKeyResponse addAnswerKey(
            AnswerKeyRequest request);

}