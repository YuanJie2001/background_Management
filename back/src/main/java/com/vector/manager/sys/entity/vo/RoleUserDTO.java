package com.vector.manager.sys.entity.vo;

import lombok.Data;

@Data
public class RoleUserDTO {

    private Long roleId;

    private Long userId;

    private String username;

    private boolean checked;

}
