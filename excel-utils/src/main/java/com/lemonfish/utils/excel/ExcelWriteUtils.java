package com.lemonfish.utils.excel;

import com.lemonfish.utils.excel.annotation.ExcelProperty;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel写工具类
 * @author linwensi
 * @date 2020-07-03 1:24 下午
 */
public class ExcelWriteUtils {

    /**
     * 列表写入EXCEL
     * @param list 待写入列表
     * @param clazz 对象类型
     * @param outputStream 输出流
     * @param ignoreTitle 是否忽略标题
     * @param <T> 泛型
     */
    public static <T> void writeToExcel(List<T> list, Class<T> clazz, OutputStream outputStream, boolean ignoreTitle) throws IllegalAccessException {
        Workbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();
        int rowIndex = 0;
        Map<String, Integer> titleIndexMap = new HashMap<String, Integer>();
        if (!ignoreTitle) {
            CellStyle titleCellStyle = getTitleCellStyle(wb);
            Row row = sheet.createRow(rowIndex++);
            Field[] fields = clazz.getDeclaredFields();
            int cellIndex = 0;
            for (Field field : fields) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                if (excelProperty == null) {
                    continue;
                }
                int index = excelProperty.index();
                String title = excelProperty.title();
                if (!"".equals(title)) {
                    Cell cell;
                    if (index != -1) {
                        cell = row.createCell(index);
                        cellIndex = index + 1;
                        if (!"".equals(title)) {
                            titleIndexMap.put(title, index);
                        }
                    } else if (!"".equals(title)) {
                        titleIndexMap.put(title, cellIndex);
                        cell = row.createCell(cellIndex ++);
                    } else {
                        continue;
                    }
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(title);
                    cell.setCellStyle(titleCellStyle);
                }
            }
        }

        for (T item : list) {
            Row row = sheet.createRow(rowIndex ++);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                if (excelProperty == null) {
                    continue;
                }
                int index = excelProperty.index();
                String title = excelProperty.title();
                String value = "";
                field.setAccessible(true);
                Cell cell;
                if (index != -1) {
                    cell = row.createCell(index);
                } else if (!"".equals(title) && !ignoreTitle) {
                    cell = row.createCell(titleIndexMap.get(title));
                } else {
                    continue;
                }
                if (field.get(item) != null) {
                    value = field.get(item).toString();
                }
                cell.setCellValue(value);
                cell.setCellType(CellType.STRING);
            }
        }
        try {
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static CellStyle getTitleCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        return cellStyle;
    }

}
