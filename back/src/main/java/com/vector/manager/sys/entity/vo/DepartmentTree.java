package com.vector.manager.sys.entity.vo;

import lombok.Data;
import com.vector.manager.core.utils.tree.Tree;

@Data
public class DepartmentTree extends Tree<Long> {

    private static final long serialVersionUID = -9189631785455640402L;

    private String pids;

    private String deptName;

    private Integer userCount;

    private Integer sort;

}
