package com.baizhi.service;

import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public String insertUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id)
                .setState("1")
                .setRegtime(new Date())
                .setLogtime(new Date());
        userDAO.insertUser(user);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-6df2985b1ccc46d2a3666e0103c2cfcf");
        goEasy.publish("myChannel", "注册成功！");
        return id;
    }

    @Override
    public void deleteUsers(String[] id) {
        userDAO.deleteUsers(id);
    }

    @Override
    public User updateUser(User user) {
        userDAO.updateUser(user);
        return user;
    }

    @Override
    public void updateUserSetLogtime(User user) {
        userDAO.updateUserSetLogtime(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectUsersAll() {
        List<User> list = userDAO.selectUsersAll();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectUsersPage(Integer page, Integer rows) {
        int start = (page - 1) * rows;
        Map<String, Object> map = new HashMap<>();
        Integer count = userDAO.selectUserCount();
        long total = (count - 1) / rows + 1;
        List<User> users = userDAO.selectUsersPage(start, rows);
        map.put("rows", users);
        map.put("page", page);
        map.put("records", count);
        map.put("total", total);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectUserCount() {
        Integer count = userDAO.selectUserCount();
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User selectUserById(String id) {
        User user = userDAO.selectUserById(id);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User selectUserByPhone(String phone) {
        User user = userDAO.selectUserByPhone(phone);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectUsersByProvinceAndCount() {
        List<User> list = userDAO.selectUsersByProvinceAndCount();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectUsersBySexAndCount(Integer[] date) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < date.length; i++) {
            List<User> users = userDAO.selectUsersBySexAndCount(date[i]);
            for (User user : users) {
                if ("1".equals(user.getSex())) map.put("m" + i, user.getNum());
                else map.put("w" + i, user.getNum());
            }
        }
        return map;
    }

}
