package com.fire.bigevent.mapper;

import com.fire.bigevent.pojo.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {

    /**
     * 新增
     * @param article
     */
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) "+
    "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    /**
     * 文章分页查询
     * @param userId
     * @param categoryId
     * @param state
     * @return
     */
    Page<Article> list(Integer userId, Integer categoryId, String state);
}
