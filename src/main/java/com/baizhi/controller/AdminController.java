package com.baizhi.controller;

import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /*@RequestMapping("/login")
    @ResponseBody
    public Admin loginController(HttpSession session,String username, String password, String enCode){
        String val = (String) session.getAttribute("val");
        System.out.println("后台验证码："+val);
        System.out.println("前台验证码："+enCode);
        if(!val.equals(enCode)) return null;
        Admin admin = adminService.login(username, password);
        System.out.println("admin:"+admin);
        if(admin!=null) {
            session.setAttribute("admin", admin);
        }
            return admin;
    }*/

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> loginController(HttpSession session, String username, String password, String enCode) {
        Map<String, Object> map = adminService.login(username, password, enCode, session);
        return map;
    }

    //安全退出
    @RequestMapping("/exitController")
    public String exitController(HttpSession session) {
        session.invalidate();
        return "redirect:/login/login.jsp";

    }

}
