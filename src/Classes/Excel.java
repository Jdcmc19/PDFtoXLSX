package Classes;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Excel {
    public static void escribirExcel(String path,ArrayList<Transaccion> trans) throws IOException,Exception{
        String[] columns = {"FECHA", "TIPO DOCUMENTO", "DESCRIPCIÓN","CREDITO/FACTURA","DEBITO/PAGO","SALDO"};

        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("TRANSACCIONES MENSUALES");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        CellStyle numbStyle = workbook.createCellStyle();
        numbStyle.setDataFormat(createHelper.createDataFormat().getFormat("###,###,###.#0"));

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for(Transaccion transac: trans) {
           // System.out.println(transac.toString());
            Row row = sheet.createRow(rowNum++);

            Date h = new SimpleDateFormat("dd/MM/yyyy").parse(transac.getFecha());
            row.createCell(0).setCellValue(h);
            row.getCell(0).setCellStyle(dateStyle);


            row.createCell(1)
                    .setCellValue(transac.getTipoDocumento());

            row.createCell(2)
                    .setCellValue(transac.getDescripcion());
            if(transac.getTipoDocumento().equals("FACTURA")){
                row.createCell(3)
                        .setCellValue(transac.getCredito());
                row.getCell(3).setCellStyle(numbStyle);
            }
            else {
                row.createCell(4)
                        .setCellValue(transac.getDebito());
                row.getCell(4).setCellStyle(numbStyle);
            }
            row.createCell(5)
                    .setCellValue(transac.getSaldo());
            row.getCell(5).setCellStyle(numbStyle);
        }
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();

    }
}
