package com.baizhi.controller;

import com.baizhi.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/code")
public class CodeController {
    //验证码
    @RequestMapping("/imageCodeAction")
    public void imageCodeAction(HttpServletResponse response, HttpSession session) throws IOException {
        //获得随机字符
        String val = ImageCodeUtil.getSecurityCode();
        //打印随机字符
        System.out.println("val生成" + val);
        //生成图片
        BufferedImage image = ImageCodeUtil.createImage(val);
        //获取响应对象
        //HttpServletResponse response = ServletActionContext.getResponse();
        //设置响应类型
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());

        session.setAttribute("val", val);
    }

}
