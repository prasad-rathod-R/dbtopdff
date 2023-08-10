package com.topdf.project.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.springframework.stereotype.Service;

import com.topdf.project.entities.Employee;

@Service
public class PdfService {
    public void stylePdfContent(List<Employee> employees, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            for (Employee employee : employees) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Set font and font size for title
                    PDFont titleFont = PDType1Font.HELVETICA_BOLD;
                    float titleFontSize = 18;
                    contentStream.setFont(titleFont, titleFontSize);
                    contentStream.setNonStrokingColor(new PDColor(new float[] { 0, 0, 1 }, PDDeviceRGB.INSTANCE)); // Blue color

                    // Add title
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 750); // Adjusted Y position
                    contentStream.showText("Employee Information");
                    contentStream.endText();

                    // Set font and font size for employee details
                    PDFont detailsFont = PDType1Font.HELVETICA;
                    float detailsFontSize = 12;
                    contentStream.setFont(detailsFont, detailsFontSize);
                    contentStream.setNonStrokingColor(new PDColor(new float[] { 0, 0, 0 }, PDDeviceRGB.INSTANCE)); // Black color

                    // Add employee details
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 700); // Adjusted Y position
                    contentStream.showText("Name: " + employee.getFirstName() + " " + employee.getLastName());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("ID: " + employee.getId());
                    contentStream.endText();
                }
            }

            document.save(new File(filePath));
        }
    }
}
