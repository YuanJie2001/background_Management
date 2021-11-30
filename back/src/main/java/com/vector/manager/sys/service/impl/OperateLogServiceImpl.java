package com.vector.manager.sys.service.impl;

import com.vector.manager.sys.entity.OperateLog;
import com.vector.manager.sys.dao.OperateLogDao;
import com.vector.manager.sys.service.IOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */

@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogDao, OperateLog> implements IOperateLogService {

}
