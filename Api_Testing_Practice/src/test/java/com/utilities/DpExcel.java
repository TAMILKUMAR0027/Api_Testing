package com.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DpExcel {

    @DataProvider(name = "vData", parallel = true)
    public static Object[][] validData() throws IOException {
        return getRows(0);
    }

    @DataProvider(name = "iData", parallel = true)
    public static Object[][] invalidData() throws IOException {
        return getRows(1, 2, 3);
    }

    @DataProvider(name = "invalidEmailData", parallel = true)
    public static Object[][] invalidEmailData() throws IOException {
        return getRows(1);
    }

    @DataProvider(name = "invalidPasswordData", parallel = true)
    public static Object[][] invalidPasswordData() throws IOException {
        return getRows(2);
    }

    @DataProvider(name = "emptyData", parallel = true)
    public static Object[][] emptyData() throws IOException {
        return getRows(3);
    }

    private static Object[][] getRows(int... rowNumbers) throws IOException {
        Object[][] arrObj = excelDataProvider();
        Object[][] selectedData = new Object[rowNumbers.length][];

        for (int i = 0; i < rowNumbers.length; i++) {
            selectedData[i] = arrObj[rowNumbers[i]];
        }

        return selectedData;
    }

    public static Object[][] excelDataProvider() throws IOException {
        Object[][] arrObj = getExcelData(
                System.getProperty("user.dir") + "/src/test/resources/TestData.xlsx", "sheet1");

        return arrObj;
    }

    private static Object[][] getExcelData(String file, String sheetName) throws IOException {
        Object[][] data = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook work = new XSSFWorkbook(fis);

            XSSFSheet sheet = work.getSheet(sheetName);
            XSSFRow row = sheet.getRow(0);

            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = row.getLastCellNum();

            Cell cell;
            data = new String[noOfRows - 1][noOfCols];

            for (int i = 1; i < noOfRows; i++) {
                row = sheet.getRow(i);

                for (int j = 0; j < noOfCols; j++) {
                    cell = row.getCell(j);

                    if (cell == null) {
                        data[i - 1][j] = "";
                    } else {
                        data[i - 1][j] = cell.getStringCellValue();
                    }
                }
            }

            work.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("The exception is : " + e.getMessage());
        }

        return data;
    }
}
