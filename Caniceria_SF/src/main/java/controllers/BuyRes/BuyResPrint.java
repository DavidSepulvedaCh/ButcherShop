package controllers.BuyRes;

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

public class BuyResPrint {
    Connection conBD;
    ConnectionBD con = new ConnectionBD();
    PreparedStatement consulta;
    ResultSet rta;


    public void printProducts() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar PDF");
            fileChooser.setSelectedFile(new File("Inventario_Animales_Comprados.pdf"));
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                String archivoPDF = archivoSeleccionado.getAbsolutePath();

                if (!archivoPDF.toLowerCase().endsWith(".pdf")) {
                    archivoPDF += ".pdf";
                }

                Document productsList = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(productsList, new FileOutputStream(archivoPDF));
                productsList.open();

                addHeader(productsList);

                PdfPTable tableProducts = createProductTable();
                fillProductTable(tableProducts);  // Llenar la tabla con los datos de la base de datos
                productsList.add(tableProducts);
                productsList.close();

                int result = JOptionPane.showConfirmDialog(null,
                        "Documento de compras creado! ¿Desea abrir el documento?",
                        "Documento creado",
                        JOptionPane.YES_NO_OPTION);

                // Abrir el archivo si el usuario selecciona "Sí"
                if (result == JOptionPane.YES_OPTION) {
                    openPDF(archivoPDF);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear el PDF: " + e.getMessage());
        }
    }

    private void addHeader(Document document) throws Exception {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setSpacingBefore(20f);

        PdfPCell imageCell = new PdfPCell();
        InputStream inputStream = getClass().getResourceAsStream("/com/mycompany/caniceria_sf/assets/logos/Logo.png");
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
        headerText.add("COMPRA DE ANIMALES PARA BENEFICIO\n");
        textCell.addElement(headerText);
        textCell.setBorder(Rectangle.NO_BORDER);
        textCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(textCell);

        document.add(headerTable);
    }

    private PdfPTable createProductTable() throws Exception {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPTable tableProducts = new PdfPTable(5);
        float[] columnWidths = {1f, 1f, 1f, 1f, 1f};
        tableProducts.setWidths(columnWidths);
        tableProducts.setWidthPercentage(100);
        tableProducts.setSpacingBefore(20f);
        tableProducts.setSpacingAfter(20f);

        addTableHeader(tableProducts, "Tipo", headerFont);
        addTableHeader(tableProducts, "Peso @", headerFont);
        addTableHeader(tableProducts, "Precio @", headerFont);
        addTableHeader(tableProducts, "Fecha", headerFont);
        addTableHeader(tableProducts, "Proveedor", headerFont);

        return tableProducts;
    }

    private void addTableHeader(PdfPTable table, String columnName, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(columnName, font));
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    private void fillProductTable(PdfPTable tableProducts) {
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        try {
            conBD = con.getConnection();
            consulta = conBD.prepareStatement("SELECT * FROM buyres");
            rta = consulta.executeQuery();

            while (rta.next()) {
                addTableCell(tableProducts, rta.getString(2), contentFont);
                addTableCell(tableProducts, rta.getString(3), contentFont);
                addTableCell(tableProducts, rta.getString(4), contentFont);
                addTableCell(tableProducts, rta.getString(5), contentFont);
                addTableCell(tableProducts, rta.getString(6), contentFont);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + e.getMessage());
        } finally {
            try {
                if (rta != null) rta.close();
                if (consulta != null) consulta.close();
                if (conBD != null) conBD.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }

    private void addTableCell(PdfPTable table, String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    private void openPDF(String archivoPDF) {
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
