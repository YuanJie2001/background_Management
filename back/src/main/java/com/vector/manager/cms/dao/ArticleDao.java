package com.vector.manager.cms.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vector.manager.cms.entity.Article;

import java.util.Map;

/**
 *
 * @author Acerola
 * @since 2021-08-01
 */

public interface ArticleDao extends BaseMapper<Article> {

    IPage<Article> search(Page page, Map<String, Object> params);

    @SqlParser(filter = true)
    void batchPublishArticles();

}
