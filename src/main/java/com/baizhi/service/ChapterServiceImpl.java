package com.baizhi.service;

import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDAO chapterDAO;

    @Override
    public String insertChapter(Chapter chapter) {
        String id = UUID.randomUUID().toString();
        chapter.setId(id);
        chapterDAO.insertChapter(chapter);
        return id;
    }

    @Override
    public void deleteChapter(Chapter chapter) {
        chapterDAO.deleteChapter(chapter);
    }

    @Override
    public void deleteChapters(String[] id) {
        chapterDAO.deleteChapters(id);
    }

    @Override
    public void deleteChaptersByAlbumId(String[] albumid) {
        chapterDAO.deleteChaptersByAlbumId(albumid);
    }

    @Override
    public void updateChapter(Chapter chapter) {
        chapterDAO.updateChapter(chapter);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> selectChaptersAll() {
        List<Chapter> list = chapterDAO.selectChaptersAll();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectChaptersPage(Integer page, Integer rows, String albumid) {
        int start = (page - 1) * rows;
        Map<String, Object> map = new HashMap<>();
        Integer count = chapterDAO.selectChapterCount(albumid);
        long total = (count - 1) / rows + 1;
        List<Chapter> chapters = chapterDAO.selectChaptersPage(start, rows, albumid);
        map.put("rows", chapters);
        map.put("page", page);
        map.put("records", count);
        map.put("total", total);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectChapterCount(String albumid) {
        Integer count = chapterDAO.selectChapterCount(albumid);
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> selectChaptersByAlbumId(String albumid) {
        List<Chapter> list = chapterDAO.selectChaptersByAlbumId(albumid);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Chapter selectChapterById(String id) {
        Chapter chapter = chapterDAO.selectChapterById(id);
        return chapter;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> selectChaptersByIds(String[] id) {
        List<Chapter> list = chapterDAO.selectChaptersByIds(id);
        return list;
    }
}
