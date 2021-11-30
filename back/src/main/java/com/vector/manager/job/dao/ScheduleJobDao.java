package com.vector.manager.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vector.manager.job.entity.ScheduleJob;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Acerola
 * @since 2021-08-04
 */
public interface ScheduleJobDao extends BaseMapper<ScheduleJob> {

    List<ScheduleJob> selectAll();

    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> params);

}
