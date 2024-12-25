package com.fire.bigevent.mapper;

import com.fire.bigevent.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    /**
     * 添加
     * @param username
     * @param password
     */
    @Insert("insert into user(username,password,create_time,update_time)" +
    " values(#{username},#{password},now(),now())")
    void add(String username, String password);
}
