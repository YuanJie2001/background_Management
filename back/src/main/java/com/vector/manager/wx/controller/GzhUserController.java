package com.vector.manager.wx.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vector.manager.core.annotation.Log;
import com.vector.manager.core.common.Constant;
import com.vector.manager.core.common.PageFactory;
import com.vector.manager.core.common.Result;
import com.vector.manager.core.utils.LmUtils;
import com.vector.manager.core.utils.UserUtil;
import com.vector.manager.wx.entity.GzhAccount;
import com.vector.manager.wx.entity.GzhUser;
import com.vector.manager.wx.service.IGzhAccountService;
import com.vector.manager.wx.service.IGzhUserService;
import com.vector.manager.wx.service.impl.SyncLock;

import java.util.Map;

/**
 * <p>
 * 粉丝表 前端控制器
 * </p>
 *
 * @author YuYue
 * @since 2020-04-18
 */
@Api(value = "粉丝表模块", tags = "粉丝表模块接口")
@RestController
@RequestMapping("/wx/gzh-user")
public class GzhUserController {

    private static final Logger log = LoggerFactory.getLogger(GzhUserController.class);
    @Autowired
    private IGzhUserService gzhUserService;
    @Autowired
    private IGzhAccountService gzhAccountService;
    @Autowired
    private SyncLock syncLock;

    @ApiOperation(value = "搜索粉丝表")
    @GetMapping("/refresh")
    public Result refresh() {
        return Result.ok().add(syncLock.getAllLock(gzhAccountService.getDefalutGzhAccount()));
    }

    @ApiOperation(value = "搜索粉丝表")
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Map<String, Object> params) {
        IPage<GzhUser> page = PageFactory.getInstance(params);
        QueryWrapper<GzhUser> wrapper = new QueryWrapper<>();
        Object prop = params.get(Constant.PROP);
        Object order = params.get(Constant.ORDER);
        if (LmUtils.isNotBlank(prop) && LmUtils.isNotBlank(order)) {
            boolean isAsc = "ascending".equals(order);
            wrapper.orderBy(true, isAsc, prop.toString());
        }

        GzhAccount gzhAccount = gzhAccountService.getDefalutGzhAccount();
        Map<String, String> allLock = syncLock.getAllLock(gzhAccount);
        if (gzhAccount == null) {
            return Result.ok().add(page).add("lock", allLock);
        }

        wrapper.lambda().eq(GzhUser::getGzhId, gzhAccount.getId());
        wrapper.lambda().eq(GzhUser::getCreateBy, UserUtil.getUserId());
        wrapper.lambda().eq(LmUtils.isNotBlank(params.get("sex")), GzhUser::getSex, params.get("sex"));
        wrapper.lambda().eq(LmUtils.isNotBlank(params.get("country")), GzhUser::getCountry, params.get("country"));
        wrapper.lambda().eq(LmUtils.isNotBlank(params.get("province")), GzhUser::getProvince, params.get("province"));
        wrapper.lambda().eq(LmUtils.isNotBlank(params.get("city")), GzhUser::getCity, params.get("city"));
        Object fuzzySearch = params.get(Constant.FUZZY_SEARCH);
        wrapper.lambda().like(LmUtils.isNotBlank(fuzzySearch), GzhUser::getNickname, fuzzySearch);

        return Result.ok().add(gzhUserService.page(page, wrapper)).add("lock", allLock);
    }

    @Log
    @ApiOperation(value = "修改用户备注")
    @RequiresPermissions("wx:gzhUser:update")
    @PostMapping("/remarkModify")
    public Result remarkModify(@RequestBody GzhUser gzhUser) {
        log.info("修改粉丝表状态, 入参：{}", gzhUser);
        try {
            getWxMpService(getGzhAccount()).getUserService().userUpdateRemark(gzhUser.getOpenId(), gzhUser.getRemark());
        } catch (WxErrorException e) {
//            return Result.fail(e.getError().getErrorMsg());
            log.error(e.getError().getErrorMsg());
        }
        gzhUserService.updateById(gzhUser);
        return Result.ok().add(gzhUser);
    }

    private GzhAccount getGzhAccount() {
        GzhAccount gzhAccount = gzhAccountService.getOne(new LambdaQueryWrapper<GzhAccount>()
                .eq(GzhAccount::getCreateBy, UserUtil.getUserId())
                .eq(GzhAccount::getDefaultAccount, 1));
        return gzhAccount;
    }

    private WxMpService getWxMpService(GzhAccount gzhAccount) {
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(gzhAccount.getWeixinAppid());
        config.setSecret(gzhAccount.getWeixinAppsecret());

        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        return wxMpService;
    }

}
