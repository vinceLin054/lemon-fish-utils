package com.lemonfish.utils.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author linwensi
 * @date 2020-07-03 11:28 上午
 */
public class ExcelTest {

    @Test
    public void testRead() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("/Users/duandian/Downloads/8a7acc22-e683-4ed2-8bb6-ebad3542cb0f.xls");
        List<Entity> entities = ExcelReadUtils.readList(inputStream, 0, false, Entity.class);
        for (Entity entity : entities) {
            System.out.println(entity);
        }
    }

    @Test
    public void testWrite() throws IllegalAccessException, FileNotFoundException {
        List<Entity> list = new ArrayList<Entity>();
        for (int i = 0;i < 5; i++) {
            Entity entity = new Entity();
            entity.setProperty1("试试");
            entity.setProperty2("看看");
            entity.setProperty3(3);
            list.add(entity);
        }
        ExcelWriteUtils.writeToExcel(list, Entity.class, new FileOutputStream("/Users/duandian/lemonfish.xls"), true);
    }

}
