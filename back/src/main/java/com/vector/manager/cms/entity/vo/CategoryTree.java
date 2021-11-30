package com.vector.manager.cms.entity.vo;

import lombok.Data;
import com.vector.manager.core.utils.tree.Tree;

import java.util.Date;


/**
 *
 * @author Acerola
 * @since 2021-08-01
 */


@Data
public class CategoryTree extends Tree<Long> {

    private String categoryName;
    private String redirectUrl;
    private String icon;
    private Integer sort;
    private Integer status;
    private String remark;
    private Date createTime;

    public CategoryTree(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryTree() {
    }

}
