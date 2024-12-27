package com.fire.bigevent.mapper;

import com.fire.bigevent.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 新增
     * @param category
     */
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) "+
    "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    /**
     * 查询文章列表
     * @param userId
     * @return
     */
    @Select("select * from category where create_user = #{userId}")
    List<Category> list(Integer userId);

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    /**
     * 更新分类
     * @param category
     */
    @Update("update category set category_name = #{categoryName},category_alias = #{categoryAlias},update_time = #{updateTime} where id = #{id}")
    void update(Category category);

    /**
     * 删除分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void delete(Integer id);
}
