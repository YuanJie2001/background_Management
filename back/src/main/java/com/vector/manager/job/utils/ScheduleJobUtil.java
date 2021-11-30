package com.vector.manager.job.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.vector.manager.core.config.ApplicationContextManager;
import com.vector.manager.core.enums.StatusEnum;
import com.vector.manager.job.entity.ScheduleJob;
import com.vector.manager.job.entity.ScheduleJobLog;
import com.vector.manager.job.service.impl.ScheduleJobLogServiceImpl;
import com.vector.manager.job.service.impl.ScheduleJobServiceImpl;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 定时任务
 */
public class ScheduleJobUtil extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private ExecutorService service = Executors.newSingleThreadExecutor();
    private ScheduleJobServiceImpl scheduleJobService = ApplicationContextManager.getBean(ScheduleJobServiceImpl.class);
    private ScheduleJobLogServiceImpl scheduleJobLogService = ApplicationContextManager.getBean(ScheduleJobLogServiceImpl.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Object object = context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);
        ScheduleJob scheduleJob = JSONUtil.toBean(JSONUtil.toJsonStr(object), ScheduleJob.class);

        ScheduleJob scheduleJobDB = scheduleJobService.getById(scheduleJob);
        if (scheduleJobDB.getCount() >= 3) {
            Long[] jobIds = {scheduleJob.getId()};
            scheduleJobService.pause(jobIds);
            logger.info("任务失败尝试次数已达上限({})，任务ID：{}", 3, scheduleJob.getId());
            return;
        }

        // 数据库保存执行记录
        ScheduleJobLog log = new ScheduleJobLog();
        log.setJobId(scheduleJob.getId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setStartTime(new Date());

        // 任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            // 执行任务
            logger.info("任务准备执行，任务ID：" + scheduleJob.getId());
            ScheduleRunnable task = new ScheduleRunnable(
                    scheduleJob.getBeanName(), scheduleJob.getMethodName(),
                    scheduleJob.getParams());
            Future<?> future = service.submit(task);

            future.get();

            // 任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setExecuteTime(times);
            // 任务状态 0：成功 1：失败
            log.setStatus(StatusEnum.YES.getValue());

            logger.info("任务执行完毕，任务ID：" + scheduleJob.getId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);

            // 任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setExecuteTime(times);

            // 任务状态 0：成功 1：失败
            log.setStatus(StatusEnum.NO.getValue());
            log.setError(StrUtil.sub(e.toString(), 0, 2000));
        } finally {
            log.setEndTime(new Date());
            scheduleJobLogService.save(log);
        }
    }
}
