package com.fire.bigevent.service;

import com.fire.bigevent.pojo.Article;
import com.fire.bigevent.pojo.PageBean;

public interface ArticleService {
    /**
     * 新增文章
     * @param article
     */
    void add(Article article);

    /**
     * 文章分页查询
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param state
     * @return
     */
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
