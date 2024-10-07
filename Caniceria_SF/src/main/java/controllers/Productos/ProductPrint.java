package controllers.Productos;

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

public class ProductPrint {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    ResultSet rta;

    public ProductPrint(){
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
            fileChooser.setSelectedFile(new File("Productos.pdf"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                String archivoPDF = archivoSeleccionado.getAbsolutePath();

                if (!archivoPDF.toLowerCase().endsWith(".pdf")) {
                    archivoPDF += ".pdf";
                }

                Document productsList = new Document();
                PdfWriter.getInstance(productsList, new FileOutputStream(archivoPDF));
                productsList.open();

                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

                PdfPTable headerTable = new PdfPTable(2);
                headerTable.setWidthPercentage(100);
                headerTable.setSpacingBefore(20f);

                PdfPCell imageCell = new PdfPCell();
                InputStream inputStream = getClass().getResourceAsStream("/assets/logos/Logo.png");
                if (inputStream != null) {
                    Image logo = Image.getInstance(ImageIO.read(inputStream), null);
                    logo.scaleAbsolute(100f, 100f);
                    imageCell.addElement(logo);
                } else {
                    System.out.println("El archivo de logo no se encontró.");
                }
                imageCell.setBorder(Rectangle.NO_BORDER);
                headerTable.addCell(imageCell);

                PdfPCell textCell = new PdfPCell();
                Paragraph headerText = new Paragraph();
                headerText.setFont(headerFont);
                headerText.add("Chavez Meat\n");
                headerText.add("Dirección: La Carrera N.de.S\n");
                headerText.add("Teléfono: 3223126566\n");
                headerText.add("PRODUCTOS REGISTRADOS\n");
                textCell.addElement(headerText);
                textCell.setBorder(Rectangle.NO_BORDER);
                textCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                headerTable.addCell(textCell);

                productsList.add(headerTable);

                PdfPTable tableProducts = new PdfPTable(3);
                tableProducts.setWidthPercentage(100);
                tableProducts.setSpacingBefore(20f);
                tableProducts.setSpacingAfter(20f);

                PdfPCell cell;

                cell = new PdfPCell(new Phrase("Código", headerFont));
                cell.setBorderColor(BaseColor.LIGHT_GRAY);
                cell.setBackgroundColor(BaseColor.GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5f);
                tableProducts.addCell(cell);

                cell = new PdfPCell(new Phrase("Producto", headerFont));
                cell.setBorderColor(BaseColor.LIGHT_GRAY);
                cell.setBackgroundColor(BaseColor.GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5f);
                tableProducts.addCell(cell);

                cell = new PdfPCell(new Phrase("Precio", headerFont));
                cell.setBorderColor(BaseColor.LIGHT_GRAY);
                cell.setBackgroundColor(BaseColor.GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5f);
                tableProducts.addCell(cell);

                try {
                    conBD = con.getConnection();
                    consulta = conBD.prepareStatement("SELECT * FROM productos");
                    ResultSet rta = consulta.executeQuery();

                    while (rta.next()) {
                        cell = new PdfPCell(new Phrase(rta.getString(4), contentFont)); // Código
                        cell.setBorderColor(BaseColor.LIGHT_GRAY);
                        cell.setPadding(5f);
                        tableProducts.addCell(cell);

                        cell = new PdfPCell(new Phrase(rta.getString(2), contentFont)); // Nombre
                        cell.setBorderColor(BaseColor.LIGHT_GRAY);
                        cell.setPadding(5f);
                        tableProducts.addCell(cell);

                        cell = new PdfPCell(new Phrase(rta.getString(3), contentFont)); // Precio
                        cell.setBorderColor(BaseColor.LIGHT_GRAY);
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setPadding(5f);
                        tableProducts.addCell(cell);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

                productsList.add(tableProducts);

                productsList.close();

                int result = JOptionPane.showConfirmDialog(null,"Documento creado! ¿Desea abrir el documento?","Documento creado", JOptionPane.YES_NO_OPTION);

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
