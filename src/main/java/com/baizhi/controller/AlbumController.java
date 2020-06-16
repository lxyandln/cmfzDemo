package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;

    @ResponseBody
    @RequestMapping("/selectAlbumsPage")
    public Map<String, Object> selectAlbumsPage(Integer page, Integer rows) {
        Map<String, Object> map = albumService.selectAlbumsPage(page, rows);
        /*Map<String, Object> map = new HashMap<>();
        Integer count = albumService.selectAlbumCount();
        long total = (count-1)/rows+1;
        List<Album> albums = albumService.selectAlbumsPage(page, rows);
        map.put("rows",albums);
        map.put("page",page);
        map.put("records",count);
        map.put("total",total);*/
        return map;
    }

    @ResponseBody
    @RequestMapping("/edit")
    public String edit(Album album, String oper, String cover, String[] id, HttpSession session) {
        if (oper.equals("add")) {
            System.out.println("add+album+" + album);
            String aid = albumService.insertAlbum(album);
            return aid;
        } else if (oper.equals("edit")) {
            Album album1 = albumService.selectAlbumById(album.getId());
            if (album.getCover() == null || "".equals(album.getCover())) {
                album.setCover(album1.getCover());
                session.setAttribute("no", "no");
            }
            album.setNewstime(album1.getNewstime());
            System.out.println("edit+album+" + album);
            albumService.updateAlbum(album);
            return album.getId();
        } else if (oper.equals("del")) {
            for (String s : id) {
                System.out.println("albumid+" + s);
            }
            albumService.deleteAlbums(id);
            chapterService.deleteChaptersByAlbumId(id);
            return null;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public void upload(MultipartFile cover, String aid, HttpSession session) {
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
        String filename = cover.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + filename;
        System.out.println("newFileName::" + newFileName);
        try {
            cover.transferTo(new File(file, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改原数据库的图片路径
        Album album = albumService.selectAlbumById(aid);
        album.setCover(newFileName);
        albumService.updateAlbum(album);
    }
}
