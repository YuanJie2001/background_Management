package com.vector.manager.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.vector.manager.core.common.Result;
import com.vector.manager.core.utils.tree.TreeUtil;
import com.vector.manager.sys.dao.DepartmentDao;
import com.vector.manager.sys.entity.Department;
import com.vector.manager.sys.entity.vo.DepartmentTree;
import com.vector.manager.sys.entity.vo.DepartmentUserDTO;
import com.vector.manager.sys.service.IDepartmentService;

import java.util.*;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */
@CacheConfig(cacheNames = {"IDepartmentService"})
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, Department> implements IDepartmentService {

    @Autowired
    private DepartmentDao departmentDao;


    @Cacheable(key = "'department_tree'")
    @Override
    public List<DepartmentTree> getDepartmentTree() {
        return TreeUtil.list2Tree(departmentDao.getDepartmentTree());
    }

    @Override
    public List<DepartmentUserDTO> handleLoadDepartmentUsers(Long depId) {
        return departmentDao.handleLoadDepartmentUsers(depId);
    }

    @Cacheable(key = "'department_tree_downdept_' + #deptId")
    @Override
    public Set<Long> getDownDeptIds(Long deptId) {
        Set<Long> deptIds = new LinkedHashSet<>();
        deptIds.add(deptId);
        List<Department> departmentList = this.list(new LambdaQueryWrapper<Department>().eq(Department::getParentId, deptId));
        if (departmentList != null && departmentList.size() > 0) {
            forLoop(departmentList, deptIds);
        }
        return deptIds;
    }

    private void forLoop(List<Department> departmentList, Set<Long> deptIds) {
        for (Department department : departmentList) {
            deptIds.add(department.getId());
            List<Department> list = this.list(new LambdaQueryWrapper<Department>().eq(Department::getParentId, department.getId()));
            if (list != null && list.size() > 0) {
                forLoop(list, deptIds);
            }
        }
    }

    @Cacheable(key = "'department_tree_updept_' + #deptId")
    @Override
    public List<Long> getUpDeptIds(Long deptId) {
        List<Long> deptIds = new ArrayList<>();
        Department department = this.getById(deptId);
        if (department == null) {
            Result.ok().add(deptIds);
        }
        if (department.getParentId() == 0) {
            deptIds.add(deptId);
            Result.ok().add(deptIds);
        }
        forLoop(deptIds, department);
        Collections.sort(deptIds);
        return deptIds;
    }

    private void forLoop(List<Long> deptIds, Department department) {
        if (department.getParentId() == 0) {
            return;
        }
        deptIds.add(department.getParentId());
        forLoop(deptIds, this.getById(department.getParentId()));
    }

    @CacheEvict(key = "'department_tree'")
    @Override
    public void saveDepartment(Department department) {
        this.save(department);
    }

    @CacheEvict(key = "'department_tree'")
    @Override
    public void updateDepartmentById(Department department) {
        this.updateById(department);
    }

    @CacheEvict(key = "'department_tree'")
    @Override
    public void removeDepartmentByIds(List<Long> ids) {
        this.removeByIds(ids);
    }

}
