package cn.nanchengyu.reggie.service;

import cn.nanchengyu.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * ClassName: EmployeeService
 * Package: cn.nanchengyu.reggie.service.impl
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/15 15:45
 * @Version 1.0
 */
public interface CategoryService extends IService<Category> {

    public void  remove(Long id);

}
