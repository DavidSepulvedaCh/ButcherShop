package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.ConnectionBD;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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
            String archivoPDF = ruta + "/Documents/Carniceria/Compras_Insumos.pdf";

            Document productsList = new Document();
            productsList.setPageSize(PageSize.A4.rotate());
            PdfWriter.getInstance(productsList, new FileOutputStream(archivoPDF));
            productsList.open();

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setSpacingBefore(20f);

            PdfPCell imageCell = new PdfPCell();
            Image logo = Image.getInstance("D:/UPB/VII/Emprendimiento/Logo.png");
            logo.scaleAbsolute(100f, 100f);
            imageCell.addElement(logo);
            imageCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(imageCell);

            PdfPCell textCell = new PdfPCell();
            Paragraph headerText = new Paragraph();
            headerText.setFont(headerFont);
            headerText.add("Chavez Meat\n");
            headerText.add("Dirección: La Carrera N.de.S\n");
            headerText.add("Teléfono: 3223126566\n");
            headerText.add("COMPRA DE INSUMOS\n");
            textCell.addElement(headerText);
            textCell.setBorder(Rectangle.NO_BORDER);
            textCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerTable.addCell(textCell);

            productsList.add(headerTable);

            // Crear tabla para productos
            PdfPTable tableProducts = new PdfPTable(6);
            float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f};
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

                    cell = new PdfPCell(new Phrase(rta.getString(6), contentFont));
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setPadding(6f);
                    tableProducts.addCell(cell);

                    cell = new PdfPCell(new Phrase(rta.getString(5), contentFont));
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

            int result = JOptionPane.showConfirmDialog(null,"Documento de compras creado! ¿Desea abrir el documento?","Documento creado", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                try {
                    File pdfFile = new File(archivoPDF);
                    if (pdfFile.exists()) {
                        Desktop.getDesktop().open(pdfFile);
                    } else {
                        JOptionPane.showMessageDialog(null, "El archivo no se encuentra en la ruta especificada.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo. " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
