package cn.nanchengyu.reggie.controller;

import cn.nanchengyu.reggie.common.R;
import cn.nanchengyu.reggie.entity.Employee;
import cn.nanchengyu.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * ClassName: EmployeeController
 * Package: cn.nanchengyu.reggie.controller
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/15 15:49
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee /*这是前端传给后端的对象数据*/) {
        //R工具类作为统一响应类处理
        //1. 将前端响应回来的密码进行加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes()); //加密后的密码仍然赋值给password，避免重复创建新变量

        //2. 根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>(); //MP创建对象
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);//username为唯一索引

        //3. 如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("登录失败");
        }

        //4.密码比对
        if(!emp.getPassword().equals(password)){
            return  R.error("密码错误");
        }

        //5. 查询员工状态
        if(emp.getStatus()== 0){
            return R.error("账号已禁用");
        }
        //6. 登录成功，将员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);



    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }



}
