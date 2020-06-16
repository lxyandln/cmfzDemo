package com.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.*;
import com.baizhi.service.*;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AlbumDAO albumDAO;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private TransportClient transportClient;
    @Autowired
    private ArticleService articleService;
    //@Autowired
    //private ArticleRepository articleRepository;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserService userService;

    @Test
    public void test1() {
        Admin admin = adminService.login("lxy", "123456");
        System.out.println(admin);
    }

    @Test
    public void test2() {
        List<Album> albums = albumDAO.selectAlbumsAll();
        for (Album album : albums) {
            System.out.println(album);
        }
    }

    @Test
    public void test3() {
        List<Album> albums = albumDAO.selectAlbumsByNewstime();
        for (Album album : albums) {
            System.out.println(album);
        }
    }

    @Test
    public void test4() {
        Banner banner = bannerService.selectBannerById("1");
        System.out.println("banner:++" + banner);
        Map<String, Object> map = bannerService.selectBannersPage(1, 3);
        Set<String> set = map.keySet();
        for (String s : set) {
            System.out.println("-------" + map.get(s));
        }
    }

    @Test
    public void test5() {
        String[] id = {"1", "2", "3"};
        List<Chapter> chapters = chapterService.selectChaptersByIds(id);
        for (Chapter chapter : chapters) {
            System.out.println(chapter);
        }
    }

    @Test
    public void test6() {
        //文件创建
        List<Banner> banners1 = bannerService.selectBannersAll();//查询出所有的轮播图信息
        List<Banner> banners = new ArrayList<Banner>();
        for (Banner banner : banners1) {
            banner.setPicture("D:\\resources\\cmfz\\src\\main\\webapp\\upload\\" + banner.getPicture());
            banners.add(banner);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图", "banner"), Banner.class, banners);//设置表格的标题和名称
        try {
            File file = new File("D:\\resources\\cmfz\\src\\main\\webapp\\download\\banners.xls");//指明文件路径
            FileOutputStream fos = new FileOutputStream(file);//利用输出流将文件写出
            workbook.write(fos);//创建banners.xls文件
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test7() {
        List<User> users = userDAO.selectUsersByProvinceAndCount();
        for (User user : users) {
            System.out.println(user.getSex());
            System.out.println(user.getProvince());
            System.out.println(user.getNum());
            System.out.println("---------------------------------------------");
        }

    }

    @Test
    public void test8() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-6df2985b1ccc46d2a3666e0103c2cfcf");
        goEasy.publish("myChannel", "Hello, GoEasy!");

    }

    @Test
    public void test9() {
        String s = userService.insertUser(new User());
        System.out.println("id：" + s);
    }

    @Test
    public void test10() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FqFFGG6s1Bt26DsaDcq", "gsLAoeUq1bOa129k7RF9YZ9nazMFIi");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "18236658095,13525371224");
        request.putQueryParameter("SignName", "笨笨纳纳");
        request.putQueryParameter("TemplateCode", "SMS_178761687");
        request.putQueryParameter("TemplateParam", "{\"code\":\"ln1314mm\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test11() {
        List<Article> articles = articleService.selectArticlesAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    @Test
    public void test100() throws Exception {
        //注意：连接tcp端口号9300
        TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("192.168.80.85"), 9300);
        //建立和elasSerach的连接
        TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(transportAddress);
        transportClient.admin().indices().prepareCreate("cmfz").get();
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

        xContentBuilder.startObject()
                .startObject("properties")
                .startObject("id")
                .field("type", "keyword")
                .endObject()
                .startObject("title")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("author")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("content")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("state")
                .field("type", "keyword")
                .endObject()
                .startObject("time")
                .field("type", "date")
                .endObject()
                .endObject()
                .endObject();
        PutMappingRequest putMappingRequest = new PutMappingRequest("cmfz").type("article").source(xContentBuilder);
        transportClient.admin().indices().putMapping(putMappingRequest).get();
    }

    @Test
    public void test101() throws IOException {
        //注意：连接tcp端口号9300
        //TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("192.168.80.85"),9300);
        //建立和elasSerach的连接
        //TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(transportAddress);
        List<Article> articles = articleService.selectArticlesAll();
        for (Article article : articles) {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
            XContentBuilder xContentBuilder1 = xContentBuilder.startObject()
                    .field("id", article.getId())
                    .field("title", article.getTitle())
                    .field("author", article.getAuthor())
                    .field("content", article.getContent())
                    .field("state", article.getState())
                    .field("time", article.getTime())
                    .endObject();
            transportClient.prepareIndex("cmfz", "article", article.getId()).setSource(xContentBuilder1).get();
        }
    }

    //102之后为springDate
    @Test
    public void test102() {//测试数据，连接
        Article article1 = new Article("1", "测试1标题", "测试1作者", "测试1内容", "测试1状态", new Date());
        /*Article article2 = new Article("2","测试2标题","测试2作者","测试2内容","测试2状态",new Date());
        Article article3 = new Article("3","测试3标题","测试3作者","测试3内容","测试3状态",new Date());
        Article article4 = new Article("4","测试4标题","测试4作者","测试4内容","测试4状态",new Date());*/

        //articleRepository.save(article1);
    }
}
