package com.vector.manager.dh.controller;


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
import com.vector.manager.core.annotation.Log;
import com.vector.manager.core.common.PageFactory;
import com.vector.manager.core.common.Result;
import com.vector.manager.dh.entity.Classify;
import com.vector.manager.dh.service.IClassifyService;
import com.vector.manager.sys.entity.dto.ChangeStatusDTO;

import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author Acerola
 * @since 2021-08-05
 */
@Api(value = "导航分类模块", tags = "导航分类模块接口")
@RestController
@RequestMapping("/dh/classify")
public class ClassifyController {

    private static final Logger log = LoggerFactory.getLogger(ClassifyController.class);
    @Autowired
    private IClassifyService classifyService;

    @ApiOperation(value = "搜索导航分类")
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Map<String, Object> params) {
        IPage<Classify> page = PageFactory.getInstance(params);
        LambdaQueryWrapper<Classify> wrapper = new LambdaQueryWrapper<>();


        return Result.ok().add(classifyService.page(page, wrapper));
    }

    @ApiOperation(value = "查看导航分类")
    @RequiresPermissions("dh:classify:show")
    @GetMapping("/show/{id}")
    public Result show(@PathVariable("id") Long id) {
        Classify classify = classifyService.getById(id);
        return Result.ok().add(classify);
    }

    @Log
    @ApiOperation(value = "创建导航分类")
    @RequiresPermissions("dh:classify:create")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody Classify classify) {
        log.info("创建导航分类, 入参：{}", classify);
        classifyService.save(classify);
        return Result.ok().add(classify);
    }

    @Log
    @ApiOperation(value = "更新导航分类")
    @RequiresPermissions("dh:classify:update")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Classify classify) {
        log.info("更新导航分类, 入参：{}", classify);
        classifyService.updateById(classify);
        return Result.ok().add(classify);
    }

    @Log
    @ApiOperation(value = "删除导航分类")
    @RequiresPermissions("dh:classify:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        log.info("删除导航分类, ids={}", ids);
        classifyService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

    @Log
    @ApiOperation(value = "修改导航分类状态")
    @RequiresPermissions("dh:classify:update")
    @PostMapping("/changeStatus")
    public Result changeStatus(@RequestBody ChangeStatusDTO changeStatusDTO) {
        log.info("修改导航分类状态, 入参：{}", changeStatusDTO);
        Classify classify = new Classify();
        BeanUtils.copyProperties(changeStatusDTO, classify);
        classifyService.updateById(classify);
        return Result.ok().add(classify);
    }

}
