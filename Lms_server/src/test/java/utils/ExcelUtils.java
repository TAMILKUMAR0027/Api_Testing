package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelUtils {

    @DataProvider(name = "data", parallel = true)
    public Object[][] excelDataProvider() throws IOException {
        return getExcelData(
                "D:\\Assessment\\Lms_server\\src\\test\\resources\\LoginData.xlsx",
                "Sheet1");
    }

    public String[][] getExcelData(String filename, String sheetName) throws IOException {

        String[][] data = null;

        try {
            FileInputStream fs = new FileInputStream(filename);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(0);

            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = row.getLastCellNum();

            data = new String[noOfRows - 1][noOfCols];

            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i < noOfRows; i++) {

                row = sheet.getRow(i);

                for (int j = 0; j < noOfCols; j++) {

                    Cell cell = row.getCell(j);

                    data[i - 1][j] = formatter.formatCellValue(cell);

                }
            }

            workbook.close();
            fs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}