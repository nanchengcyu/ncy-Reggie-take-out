package cn.nanchengyu.reggie.service.impl;

import cn.nanchengyu.reggie.dto.DishDto;
import cn.nanchengyu.reggie.entity.Dish;

import cn.nanchengyu.reggie.entity.DishFlavor;
import cn.nanchengyu.reggie.mapper.DishMapper;
import cn.nanchengyu.reggie.service.DishFlavorService;
import cn.nanchengyu.reggie.service.DishService;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Transactional //多张表的控制加入事务注解
    public void saveDishWithFlavor(DishDto dishDto) {
        //保存菜品基本信息到菜品表dish
        this.save(dishDto);

        //更新口味表
        //先从dishDto中获取菜品ID
        Long dishId = dishDto.getId();

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);


    }
}
