package com.vector.manager.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vector.manager.core.annotation.Log;
import com.vector.manager.core.common.Result;
import com.vector.manager.core.utils.PasswordUtil;
import com.vector.manager.core.utils.UserUtil;
import com.vector.manager.sys.entity.Department;
import com.vector.manager.sys.entity.User;
import com.vector.manager.sys.entity.vo.ModifyingPersonalPassword;
import com.vector.manager.sys.service.IDepartmentService;
import com.vector.manager.sys.service.IUserService;

import java.util.HashMap;
import java.util.Map;

@Api(value = "用户中心信息模块", tags = "用户中心信息模块接口")
@RestController
@RequestMapping("/sys/center/user")
public class UserCenterController {

    private static final Logger log = LoggerFactory.getLogger(UserCenterController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "加载个人信息")
    @GetMapping("/loadPersonalInfo")
    public Result loadPersonalInfo() {
        User user = userService.getById(UserUtil.getUserId());
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("phone", user.getPhone());
        userInfo.put("headImgUrl", user.getHeadImgUrl());
        userInfo.put("loginCount", user.getLoginCount());
        userInfo.put("lastLoginTime", user.getLastLoginTime());
        if (user.getDeptId() != null && user.getDeptId() > 0) {
            Department department = departmentService.getById(user.getDeptId());
            userInfo.put("dept", department.getDeptName());
        }
        return Result.ok().add(userInfo);
    }

    @Log
    @ApiOperation(value = "修改个人信息")
    @PostMapping("/modifyingPersonalInfo")
    public Result modifyingPersonalInfo(@RequestBody User user) {
        log.info("修改个人信息, 入参：{}", user);
        User updateUser = new User();
        updateUser.setId(UserUtil.getUserId());
        updateUser.setPhone(user.getPhone());
        updateUser.setNickname(user.getNickname());
        updateUser.setHeadImgUrl(user.getHeadImgUrl());
        userService.updateById(updateUser);
        return Result.ok().add(updateUser);
    }

    @Log
    @ApiOperation(value = "修改个人密码")
    @PostMapping("/modifyingPersonalPassword")
    public Result modifyingPersonalPassword(@RequestBody ModifyingPersonalPassword modifyingPersonalPassword) {
        log.info("修改个人密码, 入参：{}", modifyingPersonalPassword);
        User user = userService.getById(UserUtil.getUserId());
        if (!user.getPassword().equals(PasswordUtil.password(modifyingPersonalPassword.getOldPassword()))) {
            return Result.fail("旧密码不正确");
        }
        if (!modifyingPersonalPassword.getNewPassword().equals(modifyingPersonalPassword.getConfirmPassword())) {
            return Result.fail("确认密码不正确");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setPassword(PasswordUtil.password(modifyingPersonalPassword.getNewPassword()));
        userService.updateById(updateUser);
        return Result.ok().add(user);
    }

}
