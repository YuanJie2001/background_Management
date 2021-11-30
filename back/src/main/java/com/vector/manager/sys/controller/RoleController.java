package com.vector.manager.sys.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.vector.manager.core.annotation.Log;
import com.vector.manager.core.common.Result;
import com.vector.manager.sys.entity.dto.ChangeStatusDTO;
import com.vector.manager.sys.entity.dto.CreateUpdateRoleDTO;
import com.vector.manager.sys.entity.dto.RoleAddUserDTO;
import com.vector.manager.sys.entity.vo.RoleUserDTO;
import com.vector.manager.sys.service.IRoleService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */
@Api(value = "角色信息模块", tags = "角色信息模块接口")
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private IRoleService roleService;

    @Autowired

    @ApiOperation(value = "搜索角色信息")
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Map<String, Object> params) {
        return Result.ok().add(roleService.search(params));
    }

    @ApiOperation(value = "查看角色信息")
    @RequiresPermissions("sys:role:show")
    @GetMapping("/show/{id}")
    public Result show(@PathVariable("id") Long id) {
        return Result.ok().add(roleService.getById(id));
    }

    @ApiOperation(value = "加载角色下用户")
    @RequiresPermissions("sys:user:show")
    @PostMapping("/handleLoadRoleUsers")
    public Result handleLoadRoleUsers(@RequestBody RoleUserDTO roleUserDTO) {
        log.info("加载角色下用户, roleId={}, username={}", roleUserDTO.getRoleId(), roleUserDTO.getUsername());
        List<RoleUserDTO> roleUserDTOS = roleService.handleLoadRoleUsers(roleUserDTO.getRoleId(), roleUserDTO.getUsername());
        return Result.ok().add(roleUserDTOS);
    }

    @Log
    @ApiOperation(value = "修改角色状态")
    @RequiresPermissions("sys:role:update")
    @PostMapping("/changeStatus")
    public Result changeStatus(@RequestBody ChangeStatusDTO changeStatusDTO) {
        log.info("修改角色状态, 入参：{}", changeStatusDTO);
        roleService.updateById(changeStatusDTO.convertRole());
        return Result.ok();
    }

    @Log
    @ApiOperation(value = "创建角色信息")
    @RequiresPermissions("sys:role:create")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody CreateUpdateRoleDTO createUpdateRoleDTO) {
        log.info("创建角色信息, 入参：{}", createUpdateRoleDTO);
        roleService.saveRole(createUpdateRoleDTO);
        return Result.ok().add(createUpdateRoleDTO);
    }

    @Log
    @ApiOperation(value = "更新角色信息")
    @RequiresPermissions("sys:role:update")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody CreateUpdateRoleDTO createUpdateRoleDTO) {
        log.info("更新角色信息, 入参：{}", createUpdateRoleDTO);
        roleService.updateRole(createUpdateRoleDTO);
        return Result.ok().add(createUpdateRoleDTO);
    }

    @Log
    @ApiOperation(value = "添加角色用户")
    @RequiresPermissions("sys:role:update")
    @PostMapping("/addRoleUsers")
    public Result addRoleUsers(@Validated @RequestBody RoleAddUserDTO roleAddUserDTO) {
        log.info("添加角色用户, roleId={}, checkedKeys={}", roleAddUserDTO.getId(), roleAddUserDTO.getCheckedKeys());
        roleService.addRoleUsers(roleAddUserDTO);
        return Result.ok();
    }

    @Log
    @ApiOperation(value = "删除角色信息")
    @RequiresPermissions("sys:role:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        log.info("删除角色信息, ids={}", ids);
        roleService.removeRole(Arrays.asList(ids));
        return Result.ok();
    }

    @ApiOperation(value = "查询角色权限")
    @GetMapping("/show/perms/{id}")
    public Result showPerms(@PathVariable("id") Long id) {
        return Result.ok().add(roleService.selectRolePermissionById(id));
    }

    @ApiOperation(value = "加载所有角色")
    @GetMapping("/loadRoles")
    public Result loadRoles() {
        return Result.ok().add(roleService.getCacheListMaps());
    }

}
