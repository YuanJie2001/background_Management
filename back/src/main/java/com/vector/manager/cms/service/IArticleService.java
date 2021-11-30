package com.vector.manager.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.cms.entity.Article;

import java.util.List;
import java.util.Map;


/**
 *
 * @author Acerola
 * @since 2021-08-01
 */

public interface IArticleService extends IService<Article> {

    IPage<Article> search(Page page, Map<String, Object> params);

    void saveArticle(Article article);

    void batchPublishArticles();

    void removeArticleByIds(List<Long> ids);

}
