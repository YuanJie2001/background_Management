package com.vector.manager.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vector.manager.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author YuYue
 * @since 2020-01-12
 */
public interface UserDao extends BaseMapper<User> {

    IPage<User> search(Page<User> page, Map<String, Object> params);

    void updateUserLoginInfo(Long userId);

}
