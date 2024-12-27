package com.fire.bigevent.service;

import com.fire.bigevent.pojo.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 新增分类
     * @param category
     */
    void add(Category category);

    /**
     * 查询文章列表
     * @return
     */
    List<Category> list();

    /**
     * 根据id查询分类信息
     * @param id
     * @return
     */
    Category findById(Integer id);

    /**
     * 更新分类
     * @param category
     */
    void update(Category category);

    /**
     * 删除分类
     * @param id
     */
    void delete(Integer id);
}
