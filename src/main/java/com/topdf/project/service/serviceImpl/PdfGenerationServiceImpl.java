package com.topdf.project.service.serviceImpl;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Service;

import com.topdf.project.entities.Product;
import com.topdf.project.service.PdfGenerationService;

@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {

	@Override
	public byte[] generatePdfFromProducts(List<Product> products) throws IOException {
		try (PDDocument document = new PDDocument()) {
			PDPage page = new PDPage();
			document.addPage(page);

			try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
				contentStream.newLineAtOffset(100, 700);
				contentStream.showText("Product Data:");
				contentStream.endText();

				float margin = 50;
				float yStart = page.getMediaBox().getHeight() - margin;
				int tableWidth = 500;
				int yPosition = (int) yStart;
				int tableTopMargin = 20;
				int fontSize = 12;
				int rowHeight = 20;
				int tableHeight = rowHeight * (products.size() + 1); // Including headers

				drawTableHeader(contentStream, margin, yStart, tableWidth, tableTopMargin, fontSize);
				drawTableRows(contentStream, margin, yStart, tableWidth, tableTopMargin, rowHeight, fontSize, products);
				String watermarkText = "Confidential"; // Customize the watermark text
	            addWatermarkToPage(document, page, watermarkText);

			}

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			document.save(byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		}
	}

	private void drawTableHeader(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth,
			float tableTopMargin, int fontSize) throws IOException {
		contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);

		// Draw headers
		float yPosition = yStart - tableTopMargin;
		float xPosition = xStart;
		contentStream.beginText();
		contentStream.newLineAtOffset(xPosition, yPosition);
		contentStream.showText("ID");
		contentStream.newLineAtOffset(50, 0);
		contentStream.showText("Name");
		contentStream.newLineAtOffset(200, 0);
		contentStream.showText("Description");
		contentStream.newLineAtOffset(350, 0);
		contentStream.showText("Price");
		contentStream.newLineAtOffset(200, 0);
		contentStream.showText("Imaghe");
		contentStream.endText();

		// Draw header separator line
		yPosition -= 5;
		contentStream.setLineWidth(1f);
		contentStream.moveTo(xStart, yPosition);
		contentStream.lineTo(xStart + tableWidth, yPosition);
		contentStream.stroke();
	}

	/*
	 * private void drawImage(PDPageContentStream contentStream, float x, float y,
	 * String imageData, PDDocument document) throws IOException { if (imageData !=
	 * null) { byte[] imageBytes = Base64.getDecoder().decode(imageData);
	 * PDImageXObject image = PDImageXObject.createFromByteArray(document,
	 * imageBytes, null); contentStream.drawImage(image, 100, 100, 100, 100); //
	 * Adjust width and height as needed } }
	 */

	private void drawTableRows(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth,
			float tableTopMargin, int rowHeight, int fontSize, List<Product> products) throws IOException {
		for (Product product : products) {
			yStart -= rowHeight;
			contentStream.setFont(PDType1Font.HELVETICA, fontSize);
			contentStream.beginText();
			contentStream.newLineAtOffset(xStart, yStart);
			contentStream.showText(product.getName());
			contentStream.newLineAtOffset(150, 0);
			contentStream.showText(product.getDescription());
			contentStream.newLineAtOffset(200, 0);
			contentStream.showText(String.valueOf(product.getPrice()));
			contentStream.endText();

			//drawImage(contentStream, xStart + 400, yStart, product.getImageData(), null);
		}
	}
	
	private void addWatermarkToPage(PDDocument document, PDPage page, String watermarkText) throws IOException {
	    try (PDPageContentStream watermarkContentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
	        PDRectangle pageSize = page.getMediaBox();
	        float x = pageSize.getWidth() / 2;
	        float y = pageSize.getHeight() / 2;

	        watermarkContentStream.setFont(PDType1Font.HELVETICA_BOLD, 36);
	        watermarkContentStream.setNonStrokingColor(new Color(100, 220, 220, 100)); // Light gray color with transparency
	        watermarkContentStream.beginText();
	        watermarkContentStream.newLineAtOffset(x, y);
	        watermarkContentStream.setTextMatrix(Matrix.getRotateInstance(Math.PI / 4, 200, 100));
	        watermarkContentStream.showText(watermarkText);
	        watermarkContentStream.endText();
	    }
	}
	
	private void addDynamicPageNumber(PDDocument document, PDPage page, int currentPageIndex, int totalProducts) throws IOException {
	    PDRectangle mediaBox = page.getMediaBox();
	    float width = mediaBox.getWidth();
	    float height = mediaBox.getHeight();

	    try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
	        contentStream.setFont(PDType1Font.HELVETICA, 10);
	        contentStream.beginText();
	        contentStream.newLineAtOffset(width - 40, 20); // Adjust positioning as needed
	        contentStream.showText("Page " + (currentPageIndex + 1) + " of " + totalProducts);
	        contentStream.endText();
	    }
	}


}
