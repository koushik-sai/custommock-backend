package com.custommock.backend.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.custommock.backend.service.PdfParserService;
import com.custommock.backend.service.PdfService;
@RestController
@RequestMapping("/api/admin/pdf")
@CrossOrigin("*")
public class PdfController {

	private final PdfService pdfService;
	private final PdfParserService pdfParserService;

	public PdfController(
	        PdfService pdfService,
	        PdfParserService pdfParserService) {

	    this.pdfService = pdfService;
	    this.pdfParserService =
	            pdfParserService;
	}			
	@PostMapping("/upload")
	public String uploadPdf(
	        @RequestParam("file")
	        MultipartFile file) {

	    String text =
	            pdfService.extractText(file);

	    pdfParserService
	            .createExamFromText(text);

	    return "PDF Parsed Successfully";

	}

}