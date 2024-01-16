package cn.nanchengyu.reggie.service.impl;

import cn.nanchengyu.reggie.common.CustomException;
import cn.nanchengyu.reggie.entity.Category;
import cn.nanchengyu.reggie.entity.Dish;
import cn.nanchengyu.reggie.entity.Setmeal;
import cn.nanchengyu.reggie.mapper.CategoryMapper;
import cn.nanchengyu.reggie.service.CategoryService;
import cn.nanchengyu.reggie.service.DishService;
import cn.nanchengyu.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: EmployeeServiceImpl
 * Package: cn.nanchengyu.reggie.service.impl
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/15 15:46
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据id查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        //判断是否关联了菜品，如果关联抛出业务异常
        if (count > 0){
            throw new CustomException("当前分类关联了菜品，无法删除");

        }

        //判断分类是否关联了套餐，否则抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //根据id查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        //判断是否关联了菜品，如果关联抛出业务异常

        if (count1 > 0){
            throw new CustomException("当前分类关联了套餐，无法删除");
        }

        super.removeById(id);


    }

}
