package com.vector.manager.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.sys.entity.Department;
import com.vector.manager.sys.entity.vo.DepartmentTree;
import com.vector.manager.sys.entity.vo.DepartmentUserDTO;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Acerola
 */

public interface IDepartmentService extends IService<Department> {

    List<DepartmentTree> getDepartmentTree();

    List<DepartmentUserDTO> handleLoadDepartmentUsers(Long depId);

    Set<Long> getDownDeptIds(Long deptId);

    List<Long> getUpDeptIds(Long deptId);

    void saveDepartment(Department department);

    void updateDepartmentById(Department department);

    void removeDepartmentByIds(List<Long> ids);
}
