package cn.nanchengyu.reggie.service.impl;

import cn.nanchengyu.reggie.entity.Dish;

import cn.nanchengyu.reggie.mapper.DishMapper;
import cn.nanchengyu.reggie.service.DishService;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


}
