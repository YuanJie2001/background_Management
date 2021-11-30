package com.vector.manager.wx.service;

import com.vector.manager.wx.entity.GzhAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统公众号表 服务类
 * </p>
 *
 * @author YuYue
 * @since 2020-04-18
 */
public interface IGzhAccountService extends IService<GzhAccount> {

    GzhAccount getDefalutGzhAccount();
}
