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
import com.vector.manager.dh.entity.Website;
import com.vector.manager.dh.service.IWebsiteService;
import com.vector.manager.sys.entity.dto.ChangeStatusDTO;

import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author Acerola
 * @since 2021-08-05
 */
@Api(value = "导航网址模块", tags = "导航网址模块接口")
@RestController
@RequestMapping("/dh/website")
public class WebsiteController {

    private static final Logger log = LoggerFactory.getLogger(WebsiteController.class);
    @Autowired
    private IWebsiteService websiteService;

    @ApiOperation(value = "搜索导航网址")
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Map<String, Object> params) {
        IPage<Website> page = PageFactory.getInstance(params);
        LambdaQueryWrapper<Website> wrapper = new LambdaQueryWrapper<>();


        return Result.ok().add(websiteService.page(page, wrapper));
    }

    @ApiOperation(value = "查看导航网址")
    @RequiresPermissions("dh:website:show")
    @GetMapping("/show/{id}")
    public Result show(@PathVariable("id") Long id) {
        Website website = websiteService.getById(id);
        return Result.ok().add(website);
    }

    @Log
    @ApiOperation(value = "创建导航网址")
    @RequiresPermissions("dh:website:create")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody Website website) {
        log.info("创建导航网址, 入参：{}", website);
        websiteService.save(website);
        return Result.ok().add(website);
    }

    @Log
    @ApiOperation(value = "更新导航网址")
    @RequiresPermissions("dh:website:update")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Website website) {
        log.info("更新导航网址, 入参：{}", website);
        websiteService.updateById(website);
        return Result.ok().add(website);
    }

    @Log
    @ApiOperation(value = "删除导航网址")
    @RequiresPermissions("dh:website:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        log.info("删除导航网址, ids={}", ids);
        websiteService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

    @Log
    @ApiOperation(value = "修改导航网址状态")
    @RequiresPermissions("dh:website:update")
    @PostMapping("/changeStatus")
    public Result changeStatus(@RequestBody ChangeStatusDTO changeStatusDTO) {
        log.info("修改导航网址状态, 入参：{}", changeStatusDTO);
        Website website = new Website();
        BeanUtils.copyProperties(changeStatusDTO, website);
        websiteService.updateById(website);
        return Result.ok().add(website);
    }

}
