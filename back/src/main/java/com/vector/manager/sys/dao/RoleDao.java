package com.vector.manager.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vector.manager.sys.entity.Role;
import com.vector.manager.sys.entity.vo.PermissionTree;
import com.vector.manager.sys.entity.vo.RoleUserDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色信息 Mapper 接口
 * </p>
 *
 * @author YuYue
 * @since 2020-01-12
 */
public interface RoleDao extends BaseMapper<Role> {

    IPage<Role> search(Page page, Map<String, Object> params);

    List<PermissionTree> selectRolePermissionById(Long roleId);

    List<RoleUserDTO> handleLoadRoleUsers(Long roleId, String username);

}
