package com.topdf.project.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.topdf.project.entities.Product;
import com.topdf.project.service.PdfGenerationService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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

				// Draw table headers
				drawTableHeader(contentStream, margin, yPosition, tableWidth, tableTopMargin, fontSize);

				// Draw table rows
				drawTableRows(contentStream, margin, yPosition - tableHeight, tableWidth, tableTopMargin, rowHeight,
						fontSize, products);

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
		contentStream.endText();

		// Draw header separator line
		yPosition -= 15;
		contentStream.setLineWidth(1f);
		contentStream.moveTo(xStart, yPosition);
		contentStream.lineTo(xStart + tableWidth, yPosition);
		contentStream.stroke();
	}

	private void drawTableRows(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth,
			float tableTopMargin, int rowHeight, int fontSize, List<Product> products) throws IOException {
		contentStream.setFont(PDType1Font.HELVETICA, fontSize);

		float yPosition = yStart;
		for (Product product : products) {
			yPosition -= rowHeight;
			contentStream.beginText();
			contentStream.newLineAtOffset(xStart, yPosition);
			contentStream.showText(String.valueOf(product.getId()));
			contentStream.newLineAtOffset(50, 0);
			contentStream.showText(product.getName());
			contentStream.newLineAtOffset(200, 0);
			contentStream.showText(product.getDescription());
			contentStream.newLineAtOffset(350, 0);
			contentStream.showText(String.valueOf(product.getPrice()));
			contentStream.endText();
		}
	}
}
