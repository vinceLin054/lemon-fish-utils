package com.lemonfish.utils.excel;

import com.lemonfish.utils.excel.annotation.ExcelProperty;

/**
 * @author linwensi
 * @date 2020-07-03 11:29 上午
 */
public class Entity {

    @ExcelProperty(index = 0, title = "公司ID")
    private String property1;

    @ExcelProperty(index = 1, title = "店铺编码")
    private String property2;

    @ExcelProperty(index = 2, title = "店铺类型（1-店铺，4-异业）")
    private Integer property3;

    @Override
    public String toString() {
        return "Entity{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                ", property3=" + property3 +
                '}';
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public Integer getProperty3() {
        return property3;
    }

    public void setProperty3(Integer property3) {
        this.property3 = property3;
    }
}
