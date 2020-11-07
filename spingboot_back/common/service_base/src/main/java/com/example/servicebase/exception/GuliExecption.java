package com.example.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//生成有参构造方法
@NoArgsConstructor//无参数构造方法
public class GuliExecption extends RuntimeException {

    private Integer code;

    private String msg;


}
