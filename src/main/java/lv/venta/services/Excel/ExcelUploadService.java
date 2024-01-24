package lv.venta.services.Excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import lv.venta.models.users.Person;

public class ExcelUploadService {
    public static boolean isValidExcelFile(MultipartFile file) {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(file.getContentType());
    }

    public static List<Person> getPersonDataFromExcel(InputStream inputStream) {
        List<Person> persons = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("person");

            // Assuming column indexes for name, surname, and personcode are 0, 1, and 2
            // respectively
            int nameColumnIndex = 0;
            int surnameColumnIndex = 1;
            int personcodeColumnIndex = 2;

            int lastRowIndex = sheet.getLastRowNum();

            for (int i = 1; i <= lastRowIndex; i++) { // Start from 1 to skip the header row
                String name = sheet.getRow(i).getCell(nameColumnIndex).getStringCellValue();
                String surname = sheet.getRow(i).getCell(surnameColumnIndex).getStringCellValue();
                String personcode = sheet.getRow(i).getCell(personcodeColumnIndex).getStringCellValue();

                Person person = new Person(name, surname, personcode, null); // Replace 'null' with the appropriate User
                                                                             // object
                persons.add(person);
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return persons;
    }
}