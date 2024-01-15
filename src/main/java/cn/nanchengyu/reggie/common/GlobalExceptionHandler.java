package cn.nanchengyu.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


/**
 * ClassName: GlobalExceptionHandler
 * Package: cn.nanchengyu.reggie.common
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/15 19:53
 * @Version 1.0
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class}) //表示在这加了这两个注解的类上生效
@ResponseBody //返回json数据
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error("异常：{}", ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(""); //表示根据空格分割错误信息 表示数组的索引值所在位置
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
}
