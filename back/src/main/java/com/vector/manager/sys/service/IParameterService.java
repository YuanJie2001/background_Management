package com.vector.manager.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.sys.entity.Parameter;

/**
 *
 * @author Acerola
 */

public interface IParameterService extends IService<Parameter> {

    Parameter getParameterByName(String paramValue);

    void updateParameter(Parameter parameter);

}
