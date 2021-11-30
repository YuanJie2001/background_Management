package com.vector.manager.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.sys.entity.Generator;

import java.util.List;
import java.util.Map;

public interface GeneratorService extends IService<Generator> {

    IPage<Map<String, Object>> queryTables(long current, long size, String tableName);

    List<Generator> queryColumns(String tableName);

    void runGenerator(String moduleName, List<String> tableNameList);

    void saveOrUpdateBatchGenerator(List<Generator> generators);
}
