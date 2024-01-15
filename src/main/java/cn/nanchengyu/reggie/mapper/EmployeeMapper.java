package cn.nanchengyu.reggie.mapper;

import cn.nanchengyu.reggie.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: EmployeeMapper
 * Package: cn.nanchengyu.reggie.mapper
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/15 15:43
 * @Version 1.0
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    //继承MP 实现常见的crud方法
}
