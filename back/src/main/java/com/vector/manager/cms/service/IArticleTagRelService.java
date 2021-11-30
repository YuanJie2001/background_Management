package com.vector.manager.cms.service;

import com.vector.manager.cms.entity.ArticleTagRel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author Acerola
 * @since 2021-08-01
 */

public interface IArticleTagRelService extends IService<ArticleTagRel> {

    void updateArticleTagRel(Long articleId, List<Long> tagIds);
}
