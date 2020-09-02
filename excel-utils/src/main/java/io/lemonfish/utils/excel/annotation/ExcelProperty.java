package io.lemonfish.utils.excel.annotation;

import java.lang.annotation.*;

/**
 * Excel属性注解
 * @author linwensi
 * @date 2020-07-03 10:21 上午
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelProperty {

    /**
     * excel中的列，从0开始
     */
    int index() default -1;

    /**
     * excel的列名
     */
    String title() default "";


}
