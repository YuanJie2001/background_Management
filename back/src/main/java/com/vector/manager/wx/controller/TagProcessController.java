package com.vector.manager.wx.controller;

import cn.hutool.core.thread.ThreadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vector.manager.core.common.Result;
import com.vector.manager.wx.entity.GzhAccount;
import com.vector.manager.wx.service.IGzhAccountService;
import com.vector.manager.wx.service.IWxGzhApiService;
import com.vector.manager.wx.service.impl.SyncLock;

/**
 * <p>
 * 公众号粉丝分维解析标签 前端控制器
 * </p>
 *
 * @author YuYue
 * @since 2020-04-19
 */
@Slf4j
@Api(value = "解析新标签", tags = "解析新标签")
@RestController
@RequestMapping("/wx/gzh-user")
public class TagProcessController {

    @Autowired
    private IWxGzhApiService wxGzhApiService;
    @Autowired
    private IGzhAccountService gzhAccountService;
    @Autowired
    private SyncLock syncLock;

    @ApiOperation(value = "解析营销标签")
    @RequiresPermissions("wx:gzhuser:analysis")
    @GetMapping("/tagAnalysis")
    public Result tagAnalysis() {
        GzhAccount gzhAccount = gzhAccountService.getDefalutGzhAccount();
        if (gzhAccount == null) {
            return Result.fail("未设置默认公众号");
        }
        String lock = syncLock.getSecondlock(gzhAccount);
        if (!syncLock.lock(lock)) {
            return Result.fail("正在二次解析中，请稍等...");
        }
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                wxGzhApiService.tagAnalysis(gzhAccount, lock);
            }
        });
        return Result.ok();
    }

    @ApiOperation(value = "移除用户营销标签")
    @RequiresPermissions("wx:gzhuser:remove")
    @GetMapping("/tagRemove")
    public Result tagRemove() {
        GzhAccount gzhAccount = gzhAccountService.getDefalutGzhAccount();
        if (gzhAccount == null) {
            return Result.fail("未设置默认公众号");
        }
        String lock = syncLock.getRemovelock(gzhAccount);
        if (!syncLock.lock(lock)) {
            return Result.fail("正在批量取消中，请稍等...");
        }
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                wxGzhApiService.tagRemove(gzhAccount, lock);
            }
        });
        return Result.ok();
    }

}
