package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    String insertChapter(Chapter chapter);

    void deleteChapter(Chapter chapter);

    void deleteChapters(String[] id);//批量删除章节

    void deleteChaptersByAlbumId(String[] albumid);//因批量删除专辑而导致批量删除章节

    void updateChapter(Chapter chapter);

    List<Chapter> selectChaptersAll();//查询所有章节

    Map<String, Object> selectChaptersPage(Integer page, Integer rows, String albumid);//查询所有章节并分页

    Integer selectChapterCount(String albumid);//查询章节的总个数

    List<Chapter> selectChaptersByAlbumId(String albumid);//根据专辑id查询出其所属的所有章节

    Chapter selectChapterById(String id);//根据id精确查找章节

    List<Chapter> selectChaptersByIds(String[] id);//根据批量id查找章节
}
