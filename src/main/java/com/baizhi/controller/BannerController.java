package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @ResponseBody
    @RequestMapping("/selectBannersPage")
    public Map<String, Object> selectBannersPage(Integer page, Integer rows) {
        Map<String, Object> map = bannerService.selectBannersPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("edit")
    public String edit(Banner banner, String oper, String picture, String[] id, HttpSession session) {
        System.out.println("path:::" + picture);
        System.out.println("banner:::" + banner);
        if (oper.equals("add")) {
            String addid = bannerService.insertBanner(banner);
            return addid;
        } else if (oper.equals("edit")) {
            Banner banner1 = bannerService.selectBannerById(banner.getId());
            if (banner.getPicture() == null || "".equals(banner.getPicture())) {
                banner.setPicture(banner1.getPicture());
                session.setAttribute("no", "no");
            }
            banner.setTime(banner1.getTime());
            bannerService.updateBanner(banner);
            System.out.println("banner++" + banner);
            return banner.getId();
        } else if (oper.equals("del")) {
            bannerService.deleteBanners(id);
            //bannerService.deleteBanner(banner);
            return null;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public void upload(MultipartFile picture, String bid, HttpSession session) {
        String no = (String) session.getAttribute("no");
        if ("no".equals(no)) {
            session.setAttribute("no", "yes");
            System.out.println("直接打断，不上传新的文件" + no);
            return;
        }
        System.out.println("没有打断");
        String realPath = session.getServletContext().getRealPath("/upload/");
        System.out.println("realPath::" + realPath);
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String filename = picture.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + filename;
        System.out.println("newFileName::" + newFileName);
        try {
            picture.transferTo(new File(file, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改原数据库的图片路径
        Banner banner = bannerService.selectBannerById(bid);
        banner.setPicture(newFileName);
        bannerService.updateBanner(banner);
    }

    @RequestMapping("/create")
    public void createAndDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        //文件下载
        String name = "banners.xls";
        String realPath = request.getSession().getServletContext().getRealPath("/download");
        System.out.println();
        File file = new File(realPath + "/" + name);
        FileInputStream fis = new FileInputStream(file);
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(name, "UTF-8"));
        String extension = FilenameUtils.getExtension(name);
        String mimeType = request.getSession().getServletContext().getMimeType("." + extension);
        response.setContentType(mimeType);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(FileUtils.readFileToByteArray(file));

    }


   /* @RequestMapping("/download")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //文件下载
        String name = "banners.xls";
        String realPath = request.getSession().getServletContext().getRealPath("/download");
        System.out.println();
        File file = new File(realPath + "/" + name);
        FileInputStream fis = new FileInputStream(file);
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(name,"UTF-8"));
        String extension = FilenameUtils.getExtension(name);
        String mimeType = request.getSession().getServletContext().getMimeType("." + extension);
        response.setContentType(mimeType);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(FileUtils.readFileToByteArray(file));
    }*/
}
