package io.lemonfish.utils.excel;

import io.lemonfish.utils.excel.annotation.ExcelProperty;
import io.lemonfish.utils.excel.exception.ExcelReadException;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel读取工具类
 * @author linwensi
 * @date 2020-07-03 10:37 上午
 */
public class ExcelReadUtils {

    /**
     * 从excel读取列表
     * @param inputStream 输入流
     * @param sheetIndex sheet页 从0开始
     * @param ignoreTitle 是否忽略标题
     * @param clazz 转换类型
     * @param <T> 泛型
     */
    public static <T> List<T> readList(InputStream inputStream, int sheetIndex, boolean ignoreTitle, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            // 获得工作簿
            Workbook workbook = WorkbookFactory.create(inputStream);
            // 获得第一个sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int rows = sheet.getLastRowNum();
            Map<String, Integer> titleIndexMap = null;
            for (int i = 0; i <= rows; i++) {
                Row row = sheet.getRow(i);
                if (ignoreTitle && i == 0) {
                    titleIndexMap = getTitleIndexMap(row);
                    continue;
                }
                Field[] fields = clazz.getDeclaredFields();
                T item = clazz.newInstance();
                for (Field field : fields) {
                    ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
                    Class<?> fieldType = field.getType();
                    if (annotation == null) {
                        continue;
                    }
                    int index = annotation.index();
                    String title = annotation.title();
                    boolean b = !ignoreTitle && !"".equals(title);
                    if (index != -1) {
                        Cell cell = row.getCell(index);
                        field.setAccessible(true);
                        Constructor<?> constructor = fieldType.getConstructor(String.class);
                        String cellValue = extractCellValue(cell);
                        if (!"".equals(cellValue)) {
                            try {
                                Object value = constructor.newInstance(cellValue);
                                field.set(item, value);
                            } catch (InvocationTargetException e) {
                                throw new ExcelReadException("can not newInstance field:" + field.getName() + " with:" + cellValue);
                            }
                        }
                    } else if (b) {
                        Cell cell = row.getCell(titleIndexMap.get(title));
                        field.setAccessible(true);
                        Constructor<?> constructor = fieldType.getConstructor(String.class);
                        String cellValue = extractCellValue(cell);
                        if (!"".equals(cellValue)) {
                            Object value = constructor.newInstance(cellValue);
                            Integer titleIndex = titleIndexMap.get(title);
                            if (titleIndex != null) {
                                field.set(item, value);
                            }
                        }
                    }
                }
                if (!judgeEmpty(item)) {
                    list.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Map<String, Integer> getTitleIndexMap(Row row) {
        int number = row.getPhysicalNumberOfCells();
        Map<String, Integer> titleIndexMap = new HashMap<String, Integer>();
        for (int i = 0; i < number; i++) {
            Cell cell = row.getCell(i);
            titleIndexMap.put(extractCellValue(cell), i);
        }
        return titleIndexMap;
    }

    private static <T> boolean judgeEmpty(T t) throws IllegalAccessException {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object o = field.get(t);
            if (o != null) {
                return false;
            }
        }
        return true;
    }


    private static String extractCellValue(Cell cell) {
        String value = "";

        if (cell == null) {
            return value;
        }

        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                value = String.valueOf(df.format(cell.getNumericCellValue()));
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                break;
        }
        return value.trim();
    }

}
