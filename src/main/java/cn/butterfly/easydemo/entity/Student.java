package cn.butterfly.easydemo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 学生实体
 *
 * @author zjw
 * @date 2021-09-05
 */
@Data
@AllArgsConstructor
public class Student {

    /**
     * 学号
     */
    @ExcelProperty("学号")
    private String id;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String name;

    /**
     * 年龄
     */
    @ExcelProperty("年龄")
    private Integer age;

}
