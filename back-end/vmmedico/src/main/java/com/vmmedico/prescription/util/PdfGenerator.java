package com.vmmedico.prescription.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class    PdfGenerator {

    public static byte[] generatePrescriptionPdf(
            String hospitalName,
            String doctorName,
            String specialization,
            String qualification,
            String phone,
            List<Map<String, Object>> tablets
    ) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // 🏥 Header
        String hospName = (hospitalName != null && !hospitalName.isEmpty()) ? hospitalName : "XYZ Hospital";
        Paragraph header = new Paragraph(hospName)
                .setBold()
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER);
        doc.add(header);

        // 👨‍⚕️ Doctor Info
        Paragraph docInfo = new Paragraph(String.format(
                "Dr. %s\n%s, %s\nPhone: %s",
                doctorName, specialization, qualification, phone
        )).setTextAlignment(TextAlignment.RIGHT);
        doc.add(docInfo);

        // ──────────────── Separator
        doc.add(new LineSeparator(new SolidLine()));

        // 🧾 Prescription Table Header
        Paragraph title = new Paragraph("Prescription Details")
                .setBold()
                .setFontSize(14)
                .setMarginTop(15)
                .setMarginBottom(10);
        doc.add(title);

        // 💊 Tablets Table
        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 2, 2, 2}))
                .useAllAvailableWidth();
        table.addHeaderCell(new Cell().add(new Paragraph("Tablet Name").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Morning").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Afternoon").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Night").setBold()));

        // ✅ After creating table headers
        if (tablets != null && !tablets.isEmpty()) {
            for (Map<String, Object> t : tablets) {
                String tabletName = String.valueOf(
                        t.getOrDefault("tabletName", t.getOrDefault("name", ""))
                );
                String morning = String.valueOf(
                                t.getOrDefault("morning", ""));
                String afternoon = String.valueOf(
                                t.getOrDefault("afternoon", ""));
                String night = String.valueOf(
                                t.getOrDefault("night", ""));

                table.addCell(new Paragraph(tabletName));
                table.addCell(new Paragraph(morning));
                table.addCell(new Paragraph(afternoon));
                table.addCell(new Paragraph(night));
            }
        } else {
            table.addCell(new Cell(1, 4)
                    .add(new Paragraph("No tablets prescribed.")));
        }


        doc.add(table);
        doc.close();
        return baos.toByteArray();
    }
}
