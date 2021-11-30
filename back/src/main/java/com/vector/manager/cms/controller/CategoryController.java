package com.vector.manager.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.vector.manager.cms.entity.Category;
import com.vector.manager.cms.entity.vo.CategoryTree;
import com.vector.manager.cms.service.ICategoryService;
import com.vector.manager.core.annotation.Log;
import com.vector.manager.core.common.PageFactory;
import com.vector.manager.core.common.Result;
import com.vector.manager.core.utils.tree.TreeUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Acerola
 * @since 2021-08-01
 */


@Api(value = "文章类目模块", tags = "文章类目模块接口")
@RestController
@RequestMapping("/cms/category")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "权限树")
    @PostMapping("/tree")
    public Result tree() {
        List<Category> categoryList = categoryService.list();
        List<CategoryTree> categoryTreeList = categoryList.stream().map(category -> {
            CategoryTree categoryTree = new CategoryTree();
            BeanUtils.copyProperties(category, categoryTree);
            categoryTree.setPid(category.getParentId());
            return categoryTree;
        }).collect(Collectors.toList());
        List<CategoryTree> categoryTree = TreeUtil.list2Tree(categoryTreeList);
        return Result.ok().add(categoryTree);
    }

    @ApiOperation(value = "搜索文章类目")
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Map<String, Object> params) {
        IPage<Category> page = PageFactory.getInstance(params);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 自定义查询条件
        return Result.ok().add(categoryService.page(page, wrapper));
    }

    @ApiOperation(value = "查看文章类目")
    @RequiresPermissions("cms:category:show")
    @GetMapping("/show/{id}")
    public Result show(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        return Result.ok().add(category);
    }

    @Log
    @ApiOperation(value = "创建文章类目")
    @RequiresPermissions("cms:category:create")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody Category category) {
        log.info("创建文章类目, 入参：{}", category);
        categoryService.save(category);
        return Result.ok().add(category);
    }

    @Log
    @ApiOperation(value = "更新文章类目")
    @RequiresPermissions("cms:category:update")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Category category) {
        log.info("更新文章类目, 入参：{}", category);
        categoryService.updateById(category);
        return Result.ok().add(category);
    }

    @Log
    @ApiOperation(value = "删除文章类目")
    @RequiresPermissions("cms:category:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        log.info("删除文章类目, ids={}", ids);
        List<Category> categoryList = categoryService.list(new LambdaQueryWrapper<Category>().eq(Category::getParentId, ids[0]));
        if (categoryList != null && categoryList.size() > 0) {
            return Result.fail("请先删除子分类节点");
        }
        categoryService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

    @ApiOperation(value = "获取上级分类集")
    @GetMapping("/getPIds/{id}")
    public Result getPIds(@PathVariable Long id) {
        List<Long> pIds = new ArrayList<>();
        Category category = categoryService.getById(id);
        if (category == null) {
            Result.ok().add(pIds);
        }
        if (category.getParentId() == 0) {
            pIds.add(id);
            Result.ok().add(pIds);
        }
        getPidList(pIds, category);
        Collections.sort(pIds);
        return Result.ok().add(pIds);
    }

    private void getPidList(List<Long> pIds, Category category) {
        if (category.getParentId() == 0) {
            return;
        }
        pIds.add(category.getParentId());
        getPidList(pIds, categoryService.getById(category.getParentId()));
    }

}
