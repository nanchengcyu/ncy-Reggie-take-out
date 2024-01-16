package cn.nanchengyu.reggie.service.impl;

import cn.nanchengyu.reggie.entity.Setmeal;
import cn.nanchengyu.reggie.entity.SetmealDish;
import cn.nanchengyu.reggie.mapper.SetmealMapper;
import cn.nanchengyu.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {


}
