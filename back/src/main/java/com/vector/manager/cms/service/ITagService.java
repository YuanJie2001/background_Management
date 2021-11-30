package com.vector.manager.cms.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vector.manager.cms.entity.Article;
import com.vector.manager.cms.entity.Tag;

import java.util.List;


/**
 *
 * @author Acerola
 * @since 2021-08-03
 */

public interface ITagService extends IService<Tag> {

    void saveTag(Tag tag);

    List<Long> updateArticleTag(Long articleId, String tagNames);

    void updateTagHot(Long tagId);

    List<JSONObject> getHotTag(Integer size);

    void removeArticleTagRel(List<Article> articleList);

    void incrTagNum(List<Long> tagIds);

    void decrTagNum(List<Long> tagIds);

    String getTagNames(String tagIds);

}
