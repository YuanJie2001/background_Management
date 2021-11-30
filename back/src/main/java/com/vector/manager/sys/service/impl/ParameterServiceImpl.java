package com.vector.manager.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.vector.manager.sys.dao.ParameterDao;
import com.vector.manager.sys.entity.Parameter;
import com.vector.manager.sys.service.IParameterService;

/**
 *
 * @author Acerola
 * @since 2021-08-06
 */

@CacheConfig(cacheNames = {"ParameterService"})
@Service
public class ParameterServiceImpl extends ServiceImpl<ParameterDao, Parameter> implements IParameterService {

    @Cacheable(key = "'parameter_'+#paramName")
    @Override
    public Parameter getParameterByName(String paramName) {
        return this.getOne(new LambdaQueryWrapper<Parameter>().eq(Parameter::getParamName, paramName));
    }

    @CacheEvict(key = "'parameter_'+#parameter.paramName")
    @Override
    public void updateParameter(Parameter parameter) {
        this.updateById(parameter);
    }

}
