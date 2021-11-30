package com.vector.manager.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.vector.manager.sys.dao.LoginLogDao;
import com.vector.manager.sys.entity.LoginLog;
import com.vector.manager.sys.service.ILoginLogService;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogDao, LoginLog> implements ILoginLogService {

}
