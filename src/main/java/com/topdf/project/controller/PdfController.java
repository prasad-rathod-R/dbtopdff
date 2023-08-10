package com.topdf.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topdf.project.entities.Product;
import com.topdf.project.repository.ProductRepository;
import com.topdf.project.service.PdfGenerationService;

@RestController
@RequestMapping("/pdf")
public class PdfController {

	@Autowired
	private PdfGenerationService pdfGenerationService;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> generatePdf() throws IOException {
		List<Product> products = productRepository.findAll();
		byte[] pdfBytes = pdfGenerationService.generatePdfFromProducts(products);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "product_data.pdf");

		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}
}
