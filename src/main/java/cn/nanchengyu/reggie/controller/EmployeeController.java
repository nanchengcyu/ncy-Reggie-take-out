package cn.nanchengyu.reggie.controller;

import cn.nanchengyu.reggie.common.R;
import cn.nanchengyu.reggie.entity.Employee;
import cn.nanchengyu.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


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
        if (emp == null) {
            return R.error("登录失败");
        }

        //4.密码比对
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        //5. 查询员工状态
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        //6. 登录成功，将员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);


    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) { //前端接收JSON字符串 利用@RequestBody 将其转换为Java对象
        log.info("新增员工，员工信息：{}", employee.toString());
        //设置初始密码123456，并加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //其他字段赋值
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        //获取当前登录用户id
//        //利用getSession()方法获取当前登录用户id
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        //保存员工信息


        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) { //前端需要的数据page,pageSize,name
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        //构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件过滤器
//        if(name == null){
//            return R.error("name不能为空");
//        }
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name); //name不为空时，才会执行这个语句
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);

    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
        //通过session获取当前登录用户的id，从而修改更新人信息
//        Long empId = (Long) request.getSession().getAttribute("employee");

//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");


    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        if (id != null) {
            return R.success(employeeService.getById(id));
        }
        return R.error("查询失败");
    }


}
