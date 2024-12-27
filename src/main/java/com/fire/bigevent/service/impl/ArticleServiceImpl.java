package com.fire.bigevent.service.impl;

import com.fire.bigevent.mapper.ArticleMapper;
import com.fire.bigevent.pojo.Article;
import com.fire.bigevent.pojo.PageBean;
import com.fire.bigevent.service.ArticleService;
import com.fire.bigevent.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        // 补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String,Object> claims =  ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 1. 创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        // 2. 开启分页查询
        PageHelper.startPage(pageNum,pageSize);

        // 3. 调用mapper
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        // Page中提供了方法，可以获取PageHelp分页查询后得到的总记录数和当前数据
        Page<Article> as = articleMapper.list(userId,categoryId,state);

        // 把数据填充到PageBean对象
        pb.setTotal(as.getTotal());
        pb.setItems(as.getResult());

        return pb;

    }
}
