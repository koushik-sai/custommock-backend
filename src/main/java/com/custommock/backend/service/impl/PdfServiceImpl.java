package com.custommock.backend.service.impl;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.custommock.backend.service.PdfService;

@Service
public class PdfServiceImpl
        implements PdfService {

    @Override
    public String extractText(
            MultipartFile file) {

        try {

            PDDocument document =
                    Loader.loadPDF(
                            file.getBytes());

            PDFTextStripper stripper =
                    new PDFTextStripper();

            String text =
                    stripper.getText(
                            document);

            System.out.println(
                    "PDF TEXT LENGTH = "
                    + text.length());

            System.out.println(
                    "HAS ANSWER KEY = "
                    + text.toLowerCase()
                          .contains("answer key"));

            document.close();

            return text;

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage());

        }

    }

}