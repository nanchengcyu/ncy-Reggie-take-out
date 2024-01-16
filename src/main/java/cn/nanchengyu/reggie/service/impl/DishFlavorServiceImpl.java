package cn.nanchengyu.reggie.service.impl;

import cn.nanchengyu.reggie.entity.DishFlavor;
import cn.nanchengyu.reggie.mapper.DishFlavorMapper;
import cn.nanchengyu.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
