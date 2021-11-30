package com.vector.manager.core.aspect;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import com.vector.manager.core.annotation.Log;
import com.vector.manager.core.common.Result;
import com.vector.manager.core.utils.IpUtil;
import com.vector.manager.core.utils.UserUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecordLogAspect implements AspectApi {

    private static final Logger logger = LoggerFactory.getLogger(RecordLogAspect.class);
    private HttpServletRequest request;
    private LogService logService;

    @Override
    public Object doHandlerAspect(ProceedingJoinPoint pjp, Method method)
            throws Throwable {
        Result result = (Result) pjp.proceed();
        if (!result.get("code").equals(Result.RESPOND_SUCCEED_CODE)) {
            return result;
        }
        Log log = method.getAnnotation(Log.class);
        if (log != null) {
            String operationDetail = executeTemplate(log.value(), pjp, method);
            if (StrUtil.isBlank(operationDetail) && pjp.getArgs() != null) {
                operationDetail = JSONUtil.toJsonStr(pjp.getArgs());
            }
            Api api = AnnotationUtil.getAnnotation(pjp.getSignature().getDeclaringType(), Api.class);
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            Log.LogType type = log.type();
            switch (type) {
                case OPERATE:
                    saveOperateLog(api, apiOperation, operationDetail, log);
                    break;
                case LOGIN:
                    saveLoginLog(operationDetail);
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    // 解析SPEL
    private String executeTemplate(String template, ProceedingJoinPoint joinPoint, Method method) {
        LocalVariableTableParameterNameDiscoverer parameterNameDiscovere =
                new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscovere.getParameterNames(method);

        EvaluationContext context = new StandardEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();
        Object[] args = joinPoint.getArgs();
        if (args.length == parameterNames.length) {
            for (int i = 0, len = args.length; i < len; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        return parser.parseExpression(template, new TemplateParserContext()).getValue(
                context, String.class);
    }

    private void saveOperateLog(Api api, ApiOperation apiOperation, String operationDetail, Log _log) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                // TODO
                Map<String, Object> log = new HashMap<String, Object>();
                log.put("operateTime", new Date());
                log.put("busType", api != null ? api.value() : "");
                log.put("operateType", apiOperation != null ? apiOperation.value() : "");
                log.put("operateDetail", buildOperationDetail(operationDetail, _log));
                log.put("userId", UserUtil.loginUser().getId());
                log.put("userName", UserUtil.loginUser().getUsername());
                logService.saveOperateLog(log);
                logger.debug("操作日志：" + log);
            }
        });
    }

    private String buildOperationDetail(String operationDetail, Log log) {
        if (log.noSaveFields().length > 0 && StrUtil.isNotBlank(operationDetail)) {
            JSONArray jsonArray = new JSONArray();
            JSONArray args = JSONUtil.parseArray(operationDetail);
            for (Object arg : args) {
                JSONObject jsonObject = JSONUtil.parseObj(arg);
                for (String field : log.noSaveFields()) {
                    if (StrUtil.isNotBlank(jsonObject.get(field)+"")) {
                        jsonObject.put(field, "***");
                        break;
                    }
                }
                jsonArray.add(jsonObject);
            }
            operationDetail = jsonArray.toString();
        }
        return operationDetail;
    }

    /**
     * Debugger: 使用异步处理时，获取不到Request内容，暂且使用同步记录登录日志
     * @param operationDetail
     */
    private void saveLoginLog(String operationDetail) {
        Map<String, Object> log = new HashMap<String, Object>();
        log.put("loginTime", new Date());
        log.put("userId", UserUtil.loginUser().getId());
        log.put("userName", UserUtil.loginUser().getUsername());
        log.put("userAgent", request.getHeader("User-Agent"));
        log.put("ip", IpUtil.getIPAddress(request));
        logService.saveLoginLog(log);
        logger.debug("登陆日志：" + log);
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

}
