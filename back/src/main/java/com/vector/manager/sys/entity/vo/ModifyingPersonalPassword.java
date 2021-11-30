package com.vector.manager.sys.entity.vo;

import lombok.Data;

@Data
public class ModifyingPersonalPassword {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;

}
