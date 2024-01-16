package cn.nanchengyu.reggie.controller;


import cn.nanchengyu.reggie.common.R;
import cn.nanchengyu.reggie.entity.Category;
import cn.nanchengyu.reggie.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * ClassName: EmployeeController
 * Package: cn.nanchengyu.reggie.controller
 * Description:
 *
 * @Author 南城余
 * @Create 2024/1/15 15:49
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) { //此处前端只需要返回一个code所以泛型只需要String 就行

        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) { //前端需要的数据page,pageSize
        log.info("page = {},pageSize = {}", page, pageSize);
        //构造分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort);

        //执行查询categoryService.
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);

    }

    @DeleteMapping
    public R<String> delete(Long id) {

        log.info("删除分类，id为：{}", id);
        categoryService.removeById(id);
        return R.success("分类信息删除成功");
    }


}
