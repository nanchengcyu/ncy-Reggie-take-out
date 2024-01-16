package cn.nanchengyu.reggie.service.impl;

import cn.nanchengyu.reggie.entity.User;
import cn.nanchengyu.reggie.mapper.UserMapper;
import cn.nanchengyu.reggie.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
