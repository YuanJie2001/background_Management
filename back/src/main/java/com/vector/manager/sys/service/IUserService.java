package com.vector.manager.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vector.manager.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.sys.entity.dto.ChangeStatusDTO;
import com.vector.manager.sys.entity.dto.ChangePasswordDTO;
import com.vector.manager.sys.entity.dto.CreateUpdateUserDTO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Acerola
 */
public interface IUserService extends IService<User> {

    IPage<User> search(Map<String, Object> params);

    void saveUser(User user);

    void updateUser(User user);

    void removeUser(List<Long> asList);

    void updateUserLoginInfo(Long userId);

    void changePassword(ChangePasswordDTO changePasswordDTO);

    void changeStatus(ChangeStatusDTO changeStatusDTO);

    void createUpdateUser(CreateUpdateUserDTO createUpdateUserDTO);
}
