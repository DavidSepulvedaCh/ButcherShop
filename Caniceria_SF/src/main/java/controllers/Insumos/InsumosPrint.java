package controllers.Insumos;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.ConnectionBD;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InsumosPrint {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    ResultSet rta;

    public InsumosPrint() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printProducts() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar PDF");
            fileChooser.setSelectedFile(new File("Compras_Insumos.pdf"));
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                String archivoPDF = archivoSeleccionado.getAbsolutePath();

                if (!archivoPDF.toLowerCase().endsWith(".pdf")) {
                    archivoPDF += ".pdf";
                }

                Document productsList = new Document();
                productsList.setPageSize(PageSize.A4.rotate());
                PdfWriter.getInstance(productsList, new FileOutputStream(archivoPDF));
                productsList.open();

                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

                PdfPTable headerTable = new PdfPTable(2);
                headerTable.setWidthPercentage(100);
                headerTable.setWidths(new float[]{1f, 3f});

                PdfPCell imageCell = new PdfPCell();
                InputStream inputStream = getClass().getResourceAsStream("/com/mycompany/caniceria_sf/assets/logos/Logo.png");
                if (inputStream != null) {
                    Image logo = Image.getInstance(ImageIO.read(inputStream), null);
                    logo.scaleAbsolute(80f, 80f);
                    logo.setAlignment(Element.ALIGN_CENTER);
                    imageCell.addElement(logo);
                } else {
                    System.out.println("El archivo de logo no se encontró.");
                }
                imageCell.setBorder(Rectangle.NO_BORDER);
                imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerTable.addCell(imageCell);


                PdfPCell textCell = new PdfPCell();
                Paragraph headerText = new Paragraph();
                headerText.setFont(headerFont);
                headerText.setAlignment(Element.ALIGN_CENTER);
                headerText.add("Chavez Meat\n");
                headerText.add("Dirección: La Carrera N.de.S\n");
                headerText.add("Teléfono: 3223126566\n");
                headerText.add("COMPRA DE INSUMOS\n");
                textCell.addElement(headerText);
                textCell.setBorder(Rectangle.NO_BORDER);
                textCell.setHorizontalAlignment(Element.ALIGN_CENTER);
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
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
