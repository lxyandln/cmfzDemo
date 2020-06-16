package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @ResponseBody
    @RequestMapping("/selectChaptersPage")
    public Map<String, Object> selectChapterPage(Integer page, Integer rows, String albumid) {
        Map<String, Object> map = chapterService.selectChaptersPage(page, rows, albumid);
        return map;
    }

    @ResponseBody
    @RequestMapping("/edit")
    public String edit(Chapter chapter, String oper, String albumId, String[] id, HttpSession session) {
        System.out.println("chapter11+" + chapter);
        chapter.setAlbumid(albumId);//设置章节的专辑id
        System.out.println("chapter22+" + chapter);
        if (oper.equals("add")) {
            String cid = chapterService.insertChapter(chapter);//添加新的章节
            return cid;
        } else if (oper.equals("edit")) {
            if (chapter.getContent() == null || "".equals(chapter.getContent())) {
                Chapter chapter1 = chapterService.selectChapterById(chapter.getId());
                chapter1.setName(chapter.getName());
                chapter = chapter1;
                session.setAttribute("no", "no");
            }
            chapterService.updateChapter(chapter);
        } else if (oper.equals("del")) {
            List<Chapter> chapters = chapterService.selectChaptersByIds(id);
            double sum = 0;//需要删除的章节的大小的总和
            for (Chapter c : chapters) {
                sum = sum + c.getSize();
            }
            BigDecimal b = new BigDecimal(sum);
            sum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Album album = albumService.selectAlbumById(albumId);
            album.setSeries(album.getSeries() - id.length)//专辑集数减少
                    .setSize(new BigDecimal(album.getSize() - sum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());//专辑大小减少
            chapterService.deleteChapters(id);
            albumService.updateAlbumSetSeriesAndSize(album);
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public void upload(MultipartFile content, String cid, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String no = (String) session.getAttribute("no");
        if ("no".equals(no)) {
            session.setAttribute("no", "yes");
            System.out.println("直接打断，不上传新的文件" + no);
            return;
        }
        System.out.println("没有打断");
        String realPath = session.getServletContext().getRealPath("/download/");
        System.out.println("realPath::" + realPath);
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String filename = content.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + filename;
        System.out.println("newFileName::" + newFileName);
        try {
            content.transferTo(new File(file, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        double size1 = (double) content.getSize();
        double size = size1 / 1024 / 1024;
        BigDecimal b = new BigDecimal(size);
        size = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        AudioFile read = AudioFileIO.read(new File(realPath, newFileName));
        AudioHeader audioHeader = read.getAudioHeader();//音频的相关信息
        int trackLength = audioHeader.getTrackLength();//音频的秒数
        //修改原数据库的图片路径
        Chapter chapter = chapterService.selectChapterById(cid);
        chapter.setContent(newFileName)
                .setSize(size)
                .setDuration(trackLength);
        chapterService.updateChapter(chapter);
        //修改专辑的大小和集数
        Album album = albumService.selectAlbumById(chapter.getAlbumid());//查询出此专辑
        album.setSeries(album.getSeries() + 1)//专辑集数增加1
                .setSize(new BigDecimal(album.getSize() + size).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());//专辑大小增加
        albumService.updateAlbumSetSeriesAndSize(album);//修改专辑的集数和大小
    }

    @ResponseBody
    @RequestMapping("/download")
    public void download(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
}
