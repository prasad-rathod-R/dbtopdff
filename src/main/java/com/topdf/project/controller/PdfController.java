package com.topdf.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topdf.project.entities.Employee;
import com.topdf.project.repository.EmployeeRepository;
import com.topdf.project.service.PdfService;

@RestController
public class PdfController {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PdfService pdfService;

	@GetMapping(value = "/generate-pdf")
	public ResponseEntity<String> generatePdf() throws IOException {
		List<Employee> employees = employeeRepository.findAll();

		String filePath = "C:\\Users\\hp\\eclipse-workspace2.O\\dbtopdff\\pdf\\pdffile.pdf";
//		pdfService.savePdfToDisk(employees, filePath);
		pdfService.stylePdfContent(employees, filePath);

		return ResponseEntity.ok("PDF saved to disk: " + filePath);
	}
}