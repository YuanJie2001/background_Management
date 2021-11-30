package com.vector.manager.job.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vector.manager.core.common.Constant;
import com.vector.manager.core.common.PageFactory;
import com.vector.manager.core.common.Result;
import com.vector.manager.job.entity.ScheduleJobLog;
import com.vector.manager.job.service.IScheduleJobLogService;

import java.util.Map;
import java.util.Objects;

/**
 * 定时任务日志
 */
@Api(value = "定时任务日志", tags = "定时任务日志接口")
@RestController
@RequestMapping("/job/log")
public class ScheduleJobLogController {

    @Autowired
    private IScheduleJobLogService scheduleJobLogService;

    @ApiOperation(value = "定时任务日志列表")
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Map<String, Object> params) {
        params = PageFactory.buildMap(params);
        IPage<ScheduleJobLog> page = PageFactory.getInstance(params);
        Object jobId = params.get("jobId");
        Object status = params.get(Constant.STATUS);
        Object fuzzySearch = params.get(Constant.FUZZY_SEARCH);
        Wrapper<ScheduleJobLog> wrapper = new LambdaQueryWrapper<ScheduleJobLog>()
                .orderByDesc(ScheduleJobLog::getId)
                .eq(Objects.nonNull(jobId), ScheduleJobLog::getJobId, jobId)
                .eq(Objects.nonNull(status), ScheduleJobLog::getStatus, status)
                .and(Objects.nonNull(fuzzySearch), p ->
                        p.like(ScheduleJobLog::getBeanName, fuzzySearch)
                                .or().like(ScheduleJobLog::getMethodName, fuzzySearch));
        return Result.ok().add(scheduleJobLogService.page(page, wrapper));
    }

    @ApiOperation(value = "查看定时任务日志")
    @RequiresPermissions("job:log:show")
    @GetMapping("/show/{logId}")
    public Result show(@PathVariable("logId") Long id) {
        return Result.ok().add(scheduleJobLogService.getById(id));
    }

}
