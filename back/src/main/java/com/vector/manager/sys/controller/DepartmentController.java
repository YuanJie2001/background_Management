package com.vector.manager.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.vector.manager.core.annotation.Log;
import com.vector.manager.core.common.PageFactory;
import com.vector.manager.core.common.Result;
import com.vector.manager.sys.entity.Department;
import com.vector.manager.sys.entity.vo.DepartmentUserDTO;
import com.vector.manager.sys.service.IDepartmentService;

import java.util.*;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */
@Api(value = "部门信息模块", tags = "部门信息模块接口")
@RestController
@RequestMapping("/sys/department")
public class DepartmentController {

    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "权限树")
    @PostMapping("/tree")
    public Result tree() {
        return Result.ok().add(departmentService.getDepartmentTree());
    }

    @ApiOperation(value = "搜索部门信息")
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Map<String, Object> params) {
        IPage<Department> page = PageFactory.getInstance(params);
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        // 自定义查询条件
        return Result.ok().add(departmentService.page(page, wrapper));
    }

    @ApiOperation(value = "查看部门信息")
    @RequiresPermissions("sys:department:show")
    @GetMapping("/show/{id}")
    public Result show(@PathVariable("id") Long id) {
        Department department = departmentService.getById(id);
        return Result.ok().add(department);
    }

    @ApiOperation(value = "加载部门下用户")
    @RequiresPermissions("sys:department:show")
    @PostMapping("/handleLoadDepartmentUsers")
    public Result handleLoadDepartmentUsers(@RequestBody DepartmentUserDTO departmentUserDTO) {
        log.info("加载部门下用户, depId={}, depName={}", departmentUserDTO.getDepId(), departmentUserDTO.getDepName());
        List<DepartmentUserDTO> roleUserDTOS = departmentService.handleLoadDepartmentUsers(departmentUserDTO.getDepId());
        return Result.ok().add(roleUserDTOS);
    }

    @Log
    @ApiOperation(value = "创建部门信息")
    @RequiresPermissions("sys:department:create")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody Department department) {
        log.info("创建部门信息, 入参：{}", department);
        departmentService.saveDepartment(department);
        return Result.ok().add(department);
    }

    @Log
    @ApiOperation(value = "更新部门信息")
    @RequiresPermissions("sys:department:update")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Department department) {
        log.info("更新部门信息, 入参：{}", department);
        departmentService.updateDepartmentById(department);
        return Result.ok().add(department);
    }

    @Log
    @ApiOperation(value = "删除部门信息")
    @RequiresPermissions("sys:department:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        log.info("删除部门信息, ids={}", ids);
        List<Department> departmentList = departmentService.list(new LambdaQueryWrapper<Department>()
                .eq(Department::getParentId, ids[0]));
        if (departmentList != null && departmentList.size() > 0) {
            return Result.fail("请先删除子部门节点");
        }
        departmentService.removeDepartmentByIds(Arrays.asList(ids));
        return Result.ok();
    }

    @ApiOperation(value = "获取上级部门集")
    @GetMapping("/getPIds/{id}")
    public Result getPIds(@PathVariable Long id) {
        List<Long> pIds = departmentService.getUpDeptIds(id);
        return Result.ok().add(pIds);
    }

}
