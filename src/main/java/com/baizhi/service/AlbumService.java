package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    String insertAlbum(Album album);

    void deleteAlbum(Album album);

    void deleteAlbums(String[] id);

    void updateAlbum(Album album);

    void updateAlbumSetCover(Album album);//修改封面图片路径

    void updateAlbumSetSeriesAndSize(Album album);//因所属专辑的章节添加而变更集数和大小

    void updateAlbumSetScore(Album album);//修改评分

    List<Album> selectAlbumsAll();//查询所有专辑

    Map<String, Object> selectAlbumsPage(Integer page, Integer rows);//查询所有专辑并分页

    Integer selectAlbumCount();//查询所有专辑的总个数

    List<Album> selectAlbumsByNewstime();//根据时间排序查询出最新发布的6张专辑

    Album selectAlbumById(String id);//根据id精确查找专辑
}
