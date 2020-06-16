package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping("/insertUser")
    public void insertUser(User user) {
        String id = userService.insertUser(user);
        System.out.println("id：" + id);
    }

    @ResponseBody
    @RequestMapping("/selectUsersByProvinceAndCount")
    public List<User> selectUsersByProvinceAndCount() {
        List<User> users = userService.selectUsersByProvinceAndCount();
        return users;
    }

    @ResponseBody
    @RequestMapping("/selectUsersBySexAndCount")
    public Map<String, Object> selectUsersByLogtime(/*Integer[] date*/) {
        Integer[] date = {7, 14, 30, 90, 180, 365};
        Map<String, Object> map = userService.selectUsersBySexAndCount(date);
        return map;
    }
}
