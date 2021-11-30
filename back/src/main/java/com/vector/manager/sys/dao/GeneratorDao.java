package com.vector.manager.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.vector.manager.sys.entity.Generator;

import java.util.List;
import java.util.Map;

public interface GeneratorDao extends BaseMapper<Generator> {

    IPage<Map<String, Object>> queryTables(IPage page, @Param("tableName") String tableName);

    List<Generator> queryColumns(String tableName);

}