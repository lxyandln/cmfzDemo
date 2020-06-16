package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    Admin login(String username, String password);

    Map<String, Object> login(String username, String password, String enCode, HttpSession session);
}
