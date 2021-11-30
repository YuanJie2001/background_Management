package com.vector.manager.core.aspect;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vector.manager.sys.entity.LoginLog;
import com.vector.manager.sys.entity.OperateLog;
import com.vector.manager.sys.service.ILoginLogService;
import com.vector.manager.sys.service.IOperateLogService;
import com.vector.manager.sys.service.IUserService;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private ILoginLogService loginLogService;
    @Autowired
    private IOperateLogService operateLogService;
    @Autowired
    private IUserService userService;

    @Override
    public void saveOperateLog(Object object) {
        OperateLog operateLog = JSONUtil.toBean(JSONUtil.toJsonStr(object), OperateLog.class);
        operateLogService.save(operateLog);
    }

    @Transactional
    @Override
    public void saveLoginLog(Object object) {
        LoginLog loginLog = JSONUtil.toBean(JSONUtil.toJsonStr(object), LoginLog.class);
        loginLogService.save(loginLog);
        userService.updateUserLoginInfo(loginLog.getUserId());
    }

}
