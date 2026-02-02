package com.example.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class ExcelUtils {
    private ExcelUtils() {}

    public static Object[][] readSheet(String resourcePath, String sheetName) {
        try (InputStream is = ExcelUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Excel file not found: " + resourcePath);
            }

            try (Workbook wb = new XSSFWorkbook(is)) {
                Sheet sheet = wb.getSheet(sheetName);
                if (sheet == null) {
                    throw new RuntimeException("Sheet not found: " + sheetName);
                }

                List<Object[]> rows = new ArrayList<>();
                int last = sheet.getLastRowNum();

                // Assume row0 = header
                for (int i = 1; i <= last; i++) {
                    Row r = sheet.getRow(i);
                    if (r == null) continue;
                    String username = getString(r.getCell(0));
                    String password = getString(r.getCell(1));
                    String expected = getString(r.getCell(2));
                    rows.add(new Object[]{username, password, expected});
                }

                return rows.toArray(new Object[0][]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read excel: " + resourcePath, e);
        }
    }

    private static String getString(Cell c) {
        if (c == null) return "";
        c.setCellType(CellType.STRING);
        return c.getStringCellValue();
    }
}
