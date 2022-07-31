package com.macbook.pro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Desc: 员工实体
 * @Author: PEACEMAKER
 * @Date: 2022/6/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    private Integer id;
    private String empName;
    private LocalDate birth;
    private Double salary;
}
