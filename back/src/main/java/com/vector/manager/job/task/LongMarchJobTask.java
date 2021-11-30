package com.vector.manager.job.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vector.manager.cms.service.IArticleService;

@Component("longMarchJobTask")
public class LongMarchJobTask {

    @Autowired
    private IArticleService articleService;

    public void batchPublishArticles() {
        articleService.batchPublishArticles();
    }

}
