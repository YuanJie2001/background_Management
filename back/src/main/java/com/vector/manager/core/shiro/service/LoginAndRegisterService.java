package com.vector.manager.core.shiro.service;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vector.manager.core.common.Constant;
import com.vector.manager.core.exception.LongmarchShiroException;
import com.vector.manager.core.shiro.model.LoginInfo;
import com.vector.manager.core.shiro.model.RegisterInfo;
import com.vector.manager.sys.entity.User;
import com.vector.manager.sys.service.IParameterService;
import com.vector.manager.sys.service.IUserService;

import java.util.Date;

@Slf4j
@Service
public class LoginAndRegisterService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IParameterService parameterService;

    public void login(LoginInfo loginInfo) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        try {
            subject.login(new UsernamePasswordToken(loginInfo.getUsername(),
                    loginInfo.getPassword(), loginInfo.getRememberMe()));
        } catch (LockedAccountException e1) {
            throw new LongmarchShiroException(11, "用户已冻结，请联系管理员");
        } catch (UnknownAccountException e2) {
            throw new LongmarchShiroException(12, "用户名不存在");
        } catch (AccountException e3) {
            throw new LongmarchShiroException(13, "用户名密码不能为空");
        } catch (AuthenticationException e4) {
            throw new LongmarchShiroException(14, "用户名或密码不正确");
        }
    }

    public void register(RegisterInfo registerInfo) {
        userService.saveUser(registerUser2UserInfo(registerInfo));
    }

    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    private User registerUser2UserInfo(RegisterInfo registerInfo) {
        User user = new User();
        user.setUserType(2);
        String paramValue = parameterService.getParameterByName(Constant.DEFAULT_USER_ROLE).getParamValue();
        user.setRoleIds(JSONUtil.parseObj(paramValue).getStr("roleId"));
        user.setCreateBy(0L);
        user.setCreateTime(new Date());
        BeanUtils.copyProperties(registerInfo, user);
        return user;
    }

}
