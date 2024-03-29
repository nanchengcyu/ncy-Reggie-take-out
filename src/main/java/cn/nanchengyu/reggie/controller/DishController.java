package cn.nanchengyu.reggie.controller;

import cn.nanchengyu.reggie.common.R;
import cn.nanchengyu.reggie.dto.DishDto;
import cn.nanchengyu.reggie.entity.Dish;
import cn.nanchengyu.reggie.service.DishFlavorService;
import cn.nanchengyu.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public R<String> save(@RequestBody DishDto dishDto) {

        log.info("菜品信息：{}", dishDto.toString());
        //此处设计两张表，所以需要在service层扩展方法
        dishService.saveDishWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {

        //1.构造查询条件
        LambdaQueryWrapper<DishDto> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());

        //2.添加条件，查询状态为1的菜品
        lambdaQueryWrapper.eq(Dish::getStatus, 1);


        //3.添加排序条件，先按照商品分类排序再按照更新时间降序排列
        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        return null;


    }


}
