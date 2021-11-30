package com.vector.manager.sys.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.sys.entity.Permission;

import java.util.List;

/**
 *
 * @author Acerola
 */

public interface IPermissionService extends IService<Permission> {

    JSONObject getPermissionTree();

    void removePermissionByIds(List<Long> ids);

    List<Long> getParentIds(Long id);
}
