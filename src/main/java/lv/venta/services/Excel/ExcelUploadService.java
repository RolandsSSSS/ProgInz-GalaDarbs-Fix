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
            XSSFSheet sheet = workbook.getSheet("data");

            int nameColumnIndex = 1;
            int surnameColumnIndex = 2;
            int personcodeColumnIndex = 3;

            int lastRowIndex = sheet.getLastRowNum();

            for (int i = 1; i <= lastRowIndex; i++) {
                String name = sheet.getRow(i).getCell(nameColumnIndex).getStringCellValue();

                String surname = sheet.getRow(i).getCell(surnameColumnIndex).getStringCellValue();
                String personcode = sheet.getRow(i).getCell(personcodeColumnIndex).getStringCellValue();

                Person person = new Person(name, surname, personcode, null);

                persons.add(person);
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return persons;
    }
}