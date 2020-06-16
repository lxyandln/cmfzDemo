package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDAO {
    void insertUser(User user);

    void deleteUsers(String[] id);

    void updateUser(User user);

    void updateUserSetLogtime(User user);//修改最后登录时间

    List<User> selectUsersAll();//查询所有用户

    List<User> selectUsersPage(@Param(value = "start") Integer start, @Param(value = "rows") Integer rows);//查询所有用户并分页

    Integer selectUserCount();//查询用户的总数量

    User selectUserById(String id);//根据id精准查询用户

    //User selectUserByName(String name);//登录
    User selectUserByPhone(String phone);//登录

    List<User> selectUsersByProvinceAndCount();//确认用户及其所在地（依据省市，性别分组）

    //List<User> selectUsersByProvinceAndCount(Integer date);//通过最后登录时间确认活跃用户及其所在地
    List<User> selectUsersBySexAndCount(Integer date);//根据时间查询出活跃用户的数量（依据性别分组）
}
