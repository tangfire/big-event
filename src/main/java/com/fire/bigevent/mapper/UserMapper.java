package com.fire.bigevent.mapper;

import com.fire.bigevent.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    /**
     * 更新
     * @param user
     */
    @Update("update user set nickname = #{nickname},email = #{email},update_time = #{updateTime} where id = #{id}")
    void update(User user);

    /**
     * 更新头像
     * @param avatarUrl
     */
    @Update("update user set user_pic = #{avatarUrl},update_time = now() where id = #{id}")
    void updateAvatar(String avatarUrl,Integer id);

    /**
     * 修改密码
     * @param md5String
     * @param id
     */
    @Update("update user set password = #{md5String},update_time = now() where id = #{id}")
    void updatePwd(String md5String, Integer id);
}
