package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.ConnectionBD;

import javax.swing.*;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InsumosPrint {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    ResultSet rta;
    public void printProducts() {
        try {
            String ruta = System.getProperty("user.home");
            String archivoPDF = ruta + "/OneDrive/Escritorio/Compras_Insumos.pdf";

            Document productsList = new Document();
            productsList.setPageSize(PageSize.A4.rotate()); // Cambio: orientación horizontal
            PdfWriter.getInstance(productsList, new FileOutputStream(archivoPDF));
            productsList.open();

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            Paragraph header = new Paragraph();
            header.setAlignment(Element.ALIGN_CENTER);

            Image logo = Image.getInstance("D:/UPB/VII/Emprendimiento/Logo.png");
            logo.scaleAbsolute(100f, 100f);
            header.add(logo);

            Chunk chunk = new Chunk("Chavez Meat\n", headerFont);
            chunk.append("Dirección: La Carrera N.de.S\n");
            chunk.append("Teléfono: 3223126566\n");
            chunk.append("COMPRA DE INSUMOS\n");
            header.add(chunk);

            productsList.add(header);

            PdfPTable tableProducts = new PdfPTable(6); // Cambio: 5 columnas
            float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f}; // Cambio: anchos relativos
            tableProducts.setWidths(columnWidths);
            tableProducts.setWidthPercentage(100);
            tableProducts.setSpacingBefore(20f);
            tableProducts.setSpacingAfter(20f);

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Nombre", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6f);
            tableProducts.addCell(cell);

            cell = new PdfPCell(new Phrase("Precio $", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6f);
            tableProducts.addCell(cell);

            cell = new PdfPCell(new Phrase("Cantidad", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6f);
            tableProducts.addCell(cell);

            cell = new PdfPCell(new Phrase("Proveedor", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6f);
            tableProducts.addCell(cell);

            cell = new PdfPCell(new Phrase("Fecha", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6f);
            tableProducts.addCell(cell);

            cell = new PdfPCell(new Phrase("Descripción", headerFont));
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6f);
            tableProducts.addCell(cell);

            try {
                conBD = con.getConnection();
                consulta = conBD.prepareStatement("SELECT * FROM insumos");
                ResultSet rta = consulta.executeQuery();
                while (rta.next()) {
                    cell = new PdfPCell(new Phrase(rta.getString(2), contentFont)); // Cambio: columna "Tipo"
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setPadding(6f);
                    tableProducts.addCell(cell);

                    cell = new PdfPCell(new Phrase(rta.getString(3), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(6f);
                    tableProducts.addCell(cell);

                    cell = new PdfPCell(new Phrase(rta.getString(4), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setPadding(6f);
                    tableProducts.addCell(cell);

                    cell = new PdfPCell(new Phrase(rta.getString(5), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setPadding(6f);
                    tableProducts.addCell(cell);

                    cell = new PdfPCell(new Phrase(rta.getString(6), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setPadding(6);
                    tableProducts.addCell(cell);

                    cell = new PdfPCell(new Phrase(rta.getString(7), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setPadding(6f);
                    tableProducts.addCell(cell);
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            productsList.add(tableProducts);
            productsList.close();

            JOptionPane.showMessageDialog(null, "Documento de compras creado!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
