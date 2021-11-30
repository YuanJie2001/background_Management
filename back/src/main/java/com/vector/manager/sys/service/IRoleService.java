package com.vector.manager.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.sys.entity.Role;
import com.vector.manager.sys.entity.dto.CreateUpdateRoleDTO;
import com.vector.manager.sys.entity.dto.RoleAddUserDTO;
import com.vector.manager.sys.entity.vo.RoleDTO;
import com.vector.manager.sys.entity.vo.RoleUserDTO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Acerola
 */
public interface IRoleService extends IService<Role> {

    IPage<Role> search(Map<String, Object> params);

    Map<String, Object> selectRolePermissionById(Long roleId);

    void updateRole(CreateUpdateRoleDTO createUpdateRoleDTO);

    void saveRole(CreateUpdateRoleDTO createUpdateRoleDTO);

    void removeRole(List<Long> asList);

    List<RoleUserDTO> handleLoadRoleUsers(Long roleId, String username);

    void addRoleUsers(RoleAddUserDTO roleAddUserDTO);

    List<RoleDTO> getCacheListMaps();
}
