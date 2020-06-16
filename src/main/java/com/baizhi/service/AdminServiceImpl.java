package com.baizhi.service;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDAO adminDAO;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin login(String username, String password) {
        Admin admin = adminDAO.selectAdminByUsernameAndPassword(username, password);
        return admin;
    }

    @Override
    public Map<String, Object> login(String username, String password, String enCode, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        String val = (String) session.getAttribute("val");
        if (val.equals(enCode)) {
            Admin admin = adminDAO.selectAdminByUsername(username);
            if (admin != null) {
                if (admin.getPassword().equals(password)) {
                    session.setAttribute("admin", admin);
                    map.put("admin", admin);
                    return map;
                } else {
                    map.put("message", "密码错误！请重新输入！");
                    return map;
                }
            } else {
                map.put("message", "用户名不存在！请重新输入！");
                return map;
            }
        } else {
            map.put("message", "验证码错误！请重新输入！");
            return map;
        }
    }
}
