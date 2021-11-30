package com.vector.manager.sys.entity.dto;

import lombok.Data;
import com.vector.manager.sys.entity.Permission;
import com.vector.manager.sys.entity.Role;
import com.vector.manager.sys.entity.User;

@Data
public class ChangeStatusDTO {

    private Long id;
    private Integer status;

    public User convertUser() {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        return user;
    }

    public Role convertRole() {
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        return role;
    }

    public Permission convertPermission() {
        Permission permission = new Permission();
        permission.setId(id);
        permission.setStatus(status);
        return permission;
    }

}
