package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/kindeditor")
public class KindeditorController {

    @ResponseBody
    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile img, HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //文件上传
        String realPath = request.getSession().getServletContext().getRealPath("/upload/");//获取绝对路径
        File file = new File(realPath);//放入upload文件夹
        if (!file.exists()) {//如果不存在则创建
            file.mkdirs();
        }
        String filename = img.getOriginalFilename();//上传的文件（图片）的名字
        String newName = new Date().getTime() + "_" + filename;//防止重名进行时间戳拼接后得到新的文件名
        img.transferTo(new File(realPath, newName));//将文件进行上传
        //返回数据
        map.put("error", 0);//0是状态码，表示没有错误，可以拿到数据
        String scheme = request.getScheme();//获取协议（http）
        InetAddress localHost = Inet4Address.getLocalHost();//获取计算机名称及ip地址
        String address = localHost.getHostAddress();//获取ip地址
        int port = request.getServerPort();//获取端口号
        String path = request.getContextPath();//获取项目名（/cmfz）
        String url = scheme + "://" + address + ":" + port + path + "/upload/" + newName;//路径拼接
        map.put("url", url);
        return map;
    }

    @ResponseBody
    @RequestMapping("getAllImg")
    public Map<String, Object> getAll(HttpServletRequest request) throws UnknownHostException, UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        //找到存放图片的文件夹
        String realPath = request.getSession().getServletContext().getRealPath("/upload/");
        File file = new File(realPath);
        String[] fileNames = file.list();//文件下的所有图片的名字
        for (String fileName : fileNames) {//遍历图片的名字
            Map<String, Object> stringObjectHashMap = new HashMap<>();//向list集合中添加Map集合
            stringObjectHashMap.put("is_dir", false);//是否是一个目录
            stringObjectHashMap.put("has_file", false);//如果其是一个目录，那么此目录是否有文件
            File thisFile = new File(realPath, fileName);//通过文件名获取文件本身
            long length = thisFile.length();//获取此文件的大小
            stringObjectHashMap.put("filesize", length);//文件大小
            stringObjectHashMap.put("dir_path", "");//文件夹的路径
            stringObjectHashMap.put("is_photo", true);//是否是图片
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);//通过图片的名称截取图片类型（如根据.截取得到.jpg然后+1获取jpg）
            stringObjectHashMap.put("filetype", fileType);//图片的类型（如jpg，png等）
            stringObjectHashMap.put("filename", fileName);//文件名字
            boolean b = fileName.contains("_");//判断图片名是否包含下划线_
            String datetime = new Date().getTime() + "";//如果不包含时间戳，则赋予当前系统时间
            if (b) {//如果包含则根据_截取图片名得到时间戳
                String stringTime = fileName.split("_")[0];//得到的时间戳是字符串类型
                Long longTime = Long.valueOf(stringTime);//将字符串类型的时间戳转换为long类型
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//指定时间格式
                datetime = format.format(longTime);//将时间戳转换为指定格式
            }
            stringObjectHashMap.put("datetime", datetime);////当前图片上传的时间
            //stringObjectHashMap.put("datetime",fileName);
            list.add(stringObjectHashMap);
        }
        String scheme = request.getScheme();//获取协议（http）
        InetAddress localHost = Inet4Address.getLocalHost();//获取计算机名称及ip地址
        String address = localHost.getHostAddress();//获取ip地址
        int port = request.getServerPort();//获取端口号
        String path = request.getContextPath();//获取项目名（/cmfz）
        String url = scheme + "://" + address + ":" + port + path + "/upload/";//获取upload文件夹的路径
        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");
        map.put("current_url", url);//upload文件夹的路径
        map.put("total_count", fileNames.length);//当前文件夹（upload）下的图片数量
        map.put("file_list", list);//将list集合放入map集合中
        return map;
    }
}
