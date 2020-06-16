package com.baizhi.front;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.entity.*;
import com.baizhi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FirstController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private UserService userService;

    @RequestMapping("/first_page")
    public Map<String, Object> first_page(String uid, String type, String sub_type) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();

        if ("all".equals(type)) {
            try {
                List<Banner> banners = bannerService.selectBannersAll();
                List<Album> albums = albumService.selectAlbumsByNewstime();
                List<Article> articles = articleService.selectArticlesAll();

                map1.put("album", albums);
                map1.put("article", articles);

                map.put("header", banners);
                map.put("body", map1);
                map.put("code", 200);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 500);
            }
        }
        if ("wen".equals(type)) {
            try {
                List<Album> albums = albumService.selectAlbumsAll();

                map.put("body", albums);
                map.put("code", 200);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 500);
            }
        }
        if ("si".equals(type)) {
            List<Article> articles;
            try {
                if ("ssyj".equals(sub_type)) {
                    articles = articleService.selectArticlesAll();
                } else {
                    articles = articleService.selectArticlesAll();//xmfy，根据用户的上师id查询出此上师的最新的两篇文章
                }
                map.put("body", articles);
                map.put("code", 200);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 500);
            }
        }
        return map;
    }

    @RequestMapping("/detail")
    public Map<String, Object> detail(String id, String uidc) {
        Map<String, Object> map = new HashMap<>();
        Map<Integer, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();

        try {
            Article article = articleService.selectArticleById(id);
            map.put("link", article.getContent());
            map.put("id", id);
            map.put("ext", "");
            map.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map2.put("msg", "参数错误");
            map1.put(500, map2);
            map.put("code", map1);
        }
        return map;
    }

    @RequestMapping("/detail/wen")
    public Map<String, Object> wen(String id, String uid) {
        Map<String, Object> map = new HashMap<>();
        Map<Integer, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();

        try {
            Album album = albumService.selectAlbumById(id);
            List<Chapter> chapters = chapterService.selectChaptersByAlbumId(id);

            map.put("introduction", album);
            map.put("list", chapters);
            map.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map2.put("msg", "参数错误");
            map1.put(500, map2);
            map.put("code", map1);
        }
        return map;
    }

    @RequestMapping("/account/login")
    public Map<String, Object> login(String phone, String password, String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = userService.selectUserByPhone(phone);
            if (password.equals(user.getPassword())) {
                map.put("user", user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "-200");
            map.put("errmsg", "密码错误");
        }
        return map;
    }

    @RequestMapping("/account/regist")
    public Map<String, Object> regist(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = userService.selectUserByPhone(phone);
            if (user == null) {
                user.setPassword(password)
                        .setPhone(phone);
                userService.insertUser(user);
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "-200");
            map.put("errmsg", "该手机号已存在");
        }
        return map;
    }

    @RequestMapping("/account/update")
    public Map<String, Object> update(User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user1 = userService.updateUser(user);
            map.put("user", user1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "-200");
            map.put("errmsg", "该手机号已存在");
        }
        return map;
    }

    @RequestMapping("/member")
    public Map<String, Object> member(String uid) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<User> users = userService.selectUsersAll();
            map.put("users", users);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "-200");
            map.put("errmsg", "会员列表为空");
        }
        return map;
    }

    @RequestMapping("/identify/obtain")
    public void obtain(String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fv5rrKNyNymGBGUWDhp", "tLoFEn2gytVvrvNzjb8Ccb57tf4ozz");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "笨笨纳纳");
        request.putQueryParameter("TemplateCode", "SMS_178761687");
        request.putQueryParameter("TemplateParam", "{\"code\":\"520521\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/identify/check")
    public Map<String, Object> check(String phone, String code) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.selectUserByPhone(phone);
        if (user != null && "520521".equals(code)) {
            map.put("result", "success");
        } else {
            map.put("result", "fail");
        }
        return map;
    }
}
