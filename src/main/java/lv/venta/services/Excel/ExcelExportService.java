package lv.venta.services.Excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lv.venta.models.users.Person;

public class ExcelExportService {
    public static void exportPersonsToExcel(List<Person> persons, String filePath) {
        // Create a new workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a new sheet
        XSSFSheet sheet = workbook.createSheet("person");

        // Create the header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Surname");
        headerRow.createCell(2).setCellValue("Personcode");

        // Create the data rows
        int rowIndex = 1;
        for (Person person : persons) {
            Row dataRow = sheet.createRow(rowIndex++);
            dataRow.createCell(0).setCellValue(person.getName());
            dataRow.createCell(1).setCellValue(person.getSurname());
            dataRow.createCell(2).setCellValue(person.getPersoncode());
        }

        // Write the workbook to a file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the workbook
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}