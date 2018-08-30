package Classes;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Excel {
    public static void escribirExcel(String path,ArrayList<Transaccion> trans, Informacion informacion) throws IOException,Exception{
        String[] columns = {"FECHA", "TIPO DOCUMENTO", "DESCRIPCIÓN","CREDITO/FACTURA","DEBITO/PAGO","SALDO"};



        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("TRANSACCIONES MENSUALES");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle normal = workbook.createCellStyle();
        normal.setBorderBottom(BorderStyle.THIN);
        normal.setBorderTop(BorderStyle.THIN);
        normal.setBorderRight(BorderStyle.THIN);
        normal.setBorderLeft(BorderStyle.THIN);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        dateStyle.setBorderBottom(BorderStyle.THIN);
        dateStyle.setBorderTop(BorderStyle.THIN);
        dateStyle.setBorderRight(BorderStyle.THIN);
        dateStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle numbStyle = workbook.createCellStyle();
        numbStyle.setDataFormat(createHelper.createDataFormat().getFormat("###,###,###.#0"));
        numbStyle.setBorderBottom(BorderStyle.THIN);
        numbStyle.setBorderTop(BorderStyle.THIN);
        numbStyle.setBorderRight(BorderStyle.THIN);
        numbStyle.setBorderLeft(BorderStyle.THIN);

        Row titulo = sheet.createRow(0);
        Cell celda = titulo.createCell(0);
        celda.setCellStyle(headerCellStyle);
        celda.setCellValue("ESTADO DE CUENTA");

        Row title = sheet.createRow(2);

        String[] infor = {"CLAVE CLIENTE", "NOMBRE", "MONEDA","FECHA DE CORTE"};
        String[] inforRes = {informacion.getClaveCliente(),informacion.getNombre(),informacion.getMoneda()};

        for(int i = 0; i < infor.length; i++){
            Cell cell = title.createCell(i);
            cell.setCellValue(infor[i]);
            cell.setCellStyle(headerCellStyle);
        }

        Row titleResponses = sheet.createRow(3);

        for(int i = 0; i < inforRes.length; i++){
            Cell cell = titleResponses.createCell(i);
            cell.setCellValue(inforRes[i]);
            cell.setCellStyle(normal);
        }

        Cell cell = titleResponses.createCell(inforRes.length);
        cell.setCellStyle(dateStyle);
        cell.setCellValue(informacion.getFechaCorte());

        String[] infor2 = {"SALDO TOTAL", "SALDO CRÉDITO REVOLUTIVO", "SALDO DERECHO DE MARCA"};
        Double[] infor2res = {informacion.getSaldoTotal(), informacion.getSaldoCreditoRevolutivo(), informacion.getDerechoMarca()};

        Row title2 = sheet.createRow(4);
        Row title2res = sheet.createRow(5);

        for(int i = 0; i < infor2.length; i++){
            cell = title2.createCell(i);
            Cell cell2 = title2res.createCell(i);
            cell2.setCellValue(infor2res[i]);
            cell2.setCellStyle(numbStyle);
            cell.setCellValue(infor2[i]);
            cell.setCellStyle(headerCellStyle);
        }

        Row headerRow = sheet.createRow(7);

        for(int i = 0; i < columns.length; i++) {
            cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum =8;
        for(Transaccion transac: trans) {
           // System.out.println(transac.toString());
            Row row = sheet.createRow(rowNum++);

            Date h = new SimpleDateFormat("dd/MM/yyyy").parse(transac.getFecha());
            row.createCell(0).setCellValue(h);
            row.getCell(0).setCellStyle(dateStyle);


            row.createCell(1)
                    .setCellValue(transac.getTipoDocumento());
            row.getCell(1).setCellStyle(normal);

            row.createCell(2)
                    .setCellValue(transac.getDescripcion());
            row.getCell(2).setCellStyle(normal);

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

        Row row = sheet.createRow(rowNum++);
        row.createCell(4).setCellValue("SALDO");
        row.getCell(4).setCellStyle(headerCellStyle);
        row.createCell(5).setCellStyle(numbStyle);
        row.getCell(5).setCellValue(informacion.getSaldo());

        rowNum++;

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("CRÉD REV");
        row.getCell(0).setCellStyle(headerCellStyle);
        row.createCell(1).setCellValue(informacion.getSaldoCreditoRevolutivo2());
        row.getCell(1).setCellStyle(numbStyle);

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("DERECHO DE MARCA");
        row.getCell(0).setCellStyle(headerCellStyle);
        row.createCell(1).setCellStyle(numbStyle);
        row.getCell(1).setCellValue(informacion.getDerechoMarca2());

        rowNum++;

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("TOTAL");
        row.getCell(0).setCellStyle(headerCellStyle);
        row.createCell(1).setCellStyle(numbStyle);
        row.getCell(1).setCellValue(informacion.getTotal());


        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("DISPONIBLE CRÉDITO REV");
        row.getCell(0).setCellStyle(headerCellStyle);
        row.createCell(1).setCellStyle(numbStyle);
        row.getCell(1).setCellValue(informacion.getDisponibleCreditoRevolutivo());

        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        rowNum++;
        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(informacion.getMensaje());
        row.getCell(0).setCellStyle(normal);


        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();

    }
}
