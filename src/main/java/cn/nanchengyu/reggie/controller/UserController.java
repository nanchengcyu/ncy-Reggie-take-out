package cn.nanchengyu.reggie.controller;

import cn.nanchengyu.reggie.common.R;
import cn.nanchengyu.reggie.entity.User;
import cn.nanchengyu.reggie.service.UserService;
import cn.nanchengyu.reggie.utils.SMSUtils;
import cn.nanchengyu.reggie.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * ClassName: UserController
 * Package: cn.nanchengyu.reggie.controller
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/16 18:49
 * @Version 1.0
 */

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession httpSession) {
        //获取手机号

        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {

            //生成随机验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            log.info("短信验证码为：{}", code);


            //调用阿里云api
//            SMSUtils.sendMessage("瑞吉外卖","",phone,code.toString());
            //保存一份验证码到session中 与用户提交的做对比
            httpSession.setAttribute(phone, code);
            return R.success("手机验证码发送成功");
        }
        return R.error("手机验证码发送失败");


    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());
        //获取邮箱
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从session中获取验证码
        String codeInSession = session.getAttribute(phone).toString();
        //比较这用户输入的验证码和session中存的验证码是否一致
        if (code != null && code.equals(codeInSession)) {
            //如果输入正确，判断一下当前用户是否存在
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            //判断依据是从数据库中查询是否有其邮箱
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            //如果不存在，则创建一个，存入数据库
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                userService.save(user);
                user.setName("用户" + codeInSession);
            }
            //存个session，表示登录状态
            session.setAttribute("user",user.getId());
            //并将其作为结果返回
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
