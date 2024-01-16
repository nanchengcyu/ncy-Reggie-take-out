package cn.nanchengyu.reggie.service;

import cn.nanchengyu.reggie.dto.DishDto;
import cn.nanchengyu.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;


public interface DishService extends IService<Dish> {

    public void saveDishWithFlavor(DishDto dishDto); // 更新菜品信息，包括口味信息
}
