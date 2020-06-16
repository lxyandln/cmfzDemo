package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    String insertUser(User user);

    void deleteUsers(String[] id);

    User updateUser(User user);

    void updateUserSetLogtime(User user);//修改最后登录时间

    List<User> selectUsersAll();//查询所有用户

    Map<String, Object> selectUsersPage(Integer page, Integer rows);//查询所有用户并分页

    Integer selectUserCount();//查询用户的总数量

    User selectUserById(String id);//根据id精准查询用户

    //User selectUserByName(String name);//登录
    User selectUserByPhone(String phone);//登录

    List<User> selectUsersByProvinceAndCount();//通过最后登录时间确认活跃用户及其所在地（依据省市，性别分组）

    //List<User> selectUsersByProvinceAndCount(Integer date);//通过最后登录时间确认活跃用户及其所在地
    //Map<Integer,Integer> selectUsersByLogtime(Integer[] date);//根据时间查询出活跃用户的数量
    Map<String, Object> selectUsersBySexAndCount(Integer[] date);//根据时间查询出活跃用户的数量（依据性别分组）
}
