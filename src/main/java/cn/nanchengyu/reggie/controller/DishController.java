package cn.nanchengyu.reggie.controller;

import cn.nanchengyu.reggie.common.R;
import cn.nanchengyu.reggie.dto.DishDto;
import cn.nanchengyu.reggie.service.DishFlavorService;
import cn.nanchengyu.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: DishController
 * Package: cn.nanchengyu.reggie.controller
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/16 21:14
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        log.info("菜品信息：{}",dishDto.toString());
        //此处设计两张表，所以需要在service层扩展方法
        dishService.saveDishWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }



}
