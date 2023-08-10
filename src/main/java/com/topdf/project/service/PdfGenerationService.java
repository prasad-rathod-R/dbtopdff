package com.topdf.project.service;

import java.io.IOException;
import java.util.List;

import com.topdf.project.entities.Product;

public interface PdfGenerationService {
	byte[] generatePdfFromProducts(List<Product> products) throws IOException;
}