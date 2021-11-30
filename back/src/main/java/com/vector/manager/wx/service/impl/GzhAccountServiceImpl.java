package com.vector.manager.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.vector.manager.core.utils.UserUtil;
import com.vector.manager.wx.entity.GzhAccount;
import com.vector.manager.wx.dao.GzhAccountDao;
import com.vector.manager.wx.service.IGzhAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统公众号表 服务实现类
 * </p>
 *
 * @author YuYue
 * @since 2020-04-18
 */
@Service
public class GzhAccountServiceImpl extends ServiceImpl<GzhAccountDao, GzhAccount> implements IGzhAccountService {

    @Override
    public GzhAccount getDefalutGzhAccount() {
        return this.getOne(new LambdaQueryWrapper<GzhAccount>()
                .eq(GzhAccount::getDefaultAccount, 1)
                .eq(GzhAccount::getCreateBy, UserUtil.getUserId()));
    }

}
