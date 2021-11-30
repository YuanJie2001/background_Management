package com.vector.manager.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vector.manager.sys.entity.Department;
import com.vector.manager.sys.entity.vo.DepartmentTree;
import com.vector.manager.sys.entity.vo.DepartmentUserDTO;

import java.util.List;

/**
 * <p>
 * 部门信息 Mapper 接口
 * </p>
 *
 * @author YuYue
 * @since 2020-02-05
 */
public interface DepartmentDao extends BaseMapper<Department> {

    List<DepartmentTree> getDepartmentTree();

    List<DepartmentUserDTO> handleLoadDepartmentUsers(Long depId);
}
