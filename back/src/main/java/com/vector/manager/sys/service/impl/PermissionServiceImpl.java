package com.vector.manager.sys.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.vector.manager.core.common.Result;
import com.vector.manager.core.exception.LongmarchException;
import com.vector.manager.core.utils.tree.TreeUtil;
import com.vector.manager.sys.dao.PermissionDao;
import com.vector.manager.sys.entity.Permission;
import com.vector.manager.sys.entity.vo.PermissionTree;
import com.vector.manager.sys.service.IPermissionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements IPermissionService {

    @Override
    public JSONObject getPermissionTree() {
        JSONObject tree = new JSONObject();
        List<Permission> permissionAllList = this.list(new LambdaQueryWrapper<Permission>().orderByAsc(Permission::getSort));
        List<Permission> menuList = permissionAllList.stream().filter(p -> p.getType() != 2).collect(Collectors.toList());

        List<PermissionTree> permissionAllTreeList = permissionAllList.stream().map(permission -> {
            PermissionTree permissionTree = new PermissionTree();
            BeanUtils.copyProperties(permission, permissionTree);
            permissionTree.setPid(permission.getParentId());
            permissionTree.setPids(permission.getParentIds());
            return permissionTree;
        }).collect(Collectors.toList());
        List<PermissionTree> permissionTrees = TreeUtil.list2Tree(permissionAllTreeList);
        tree.put("permissionTrees", permissionTrees);

        List<PermissionTree> menuAllTreeList = menuList.stream().map(permission -> {
            PermissionTree permissionTree = new PermissionTree();
            BeanUtils.copyProperties(permission, permissionTree);
            permissionTree.setPid(permission.getParentId());
            permissionTree.setPids(permission.getParentIds());
            return permissionTree;
        }).collect(Collectors.toList());
        List<PermissionTree> permsList = TreeUtil.list2Tree(menuAllTreeList);
        tree.put("permsList", permsList);
        return tree;
    }

    @Override
    public void removePermissionByIds(List<Long> ids) {
        List<Permission> permissionList = this.list(new LambdaQueryWrapper<Permission>().eq(Permission::getParentId, ids.get(0)));
        if (permissionList != null && permissionList.size() > 0) {
            throw new LongmarchException("请先删除子分类节点");
        }
        this.removeByIds(ids);
    }

    @Override
    public List<Long> getParentIds(Long id) {
        List<Long> pIds = new ArrayList<>();
        Permission permission = this.getById(id);
        if (permission == null) {
            Result.ok().add(pIds);
        }
        if (permission.getParentId() == 0) {
            pIds.add(id);
            Result.ok().add(pIds);
        }
        getPidList(pIds, permission);
        Collections.sort(pIds);
        return pIds;
    }

    private void getPidList(List<Long> pIds, Permission permission) {
        if (permission.getParentId() == 0) {
            return;
        }
        pIds.add(permission.getParentId());
        getPidList(pIds, this.getById(permission.getParentId()));
    }

}
