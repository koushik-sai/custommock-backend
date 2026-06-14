package com.custommock.backend.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	private static final Logger log = LoggerFactory.getLogger(PdfController.class);

	private final PdfService pdfService;
	private final PdfParserService pdfParserService;

	// hard limit in bytes (50MB)
	private static final long MAX_BYTES = 50L * 1024L * 1024L;

	public PdfController(
			PdfService pdfService,
			PdfParserService pdfParserService) {

		this.pdfService = pdfService;
		this.pdfParserService = pdfParserService;
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadPdf(@RequestParam("file") MultipartFile file) {

		if (file == null || file.isEmpty()) {
			return badRequest("No file uploaded");
		}

		if (file.getSize() > MAX_BYTES) {
			return badRequest("File exceeds maximum allowed size of 50MB");
		}

		String contentType = file.getContentType();
		if (contentType == null || !contentType.equalsIgnoreCase("application/pdf")) {
			// allow files with missing contentType if filename ends with .pdf
			String name = file.getOriginalFilename();
			if (name == null || !name.toLowerCase().endsWith(".pdf")) {
				return badRequest("Uploaded file is not a PDF");
			}
		}

		try {
			String text = pdfService.extractText(file);

			pdfParserService.createExamFromText(text);

			Map<String, Object> resp = new HashMap<>();
			resp.put("status", "success");
			resp.put("message", "PDF parsed successfully");
			return ResponseEntity.ok(resp);

		} catch (Exception ex) {
			log.error("Failed to parse uploaded PDF", ex);
			Map<String, Object> err = new HashMap<>();
			err.put("status", "error");
			err.put("message", "Failed to parse PDF: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}

	}

	private ResponseEntity<Map<String, Object>> badRequest(String message) {
		Map<String, Object> err = new HashMap<>();
		err.put("status", "error");
		err.put("message", message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}