package com.baizhi.service;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDAO albumDAO;

    @Override
    public String insertAlbum(Album album) {
        String id = UUID.randomUUID().toString();
        album.setId(id);
        album.setNewstime(new Date());
        albumDAO.insertAlbum(album);
        return id;
    }

    @Override
    public void deleteAlbum(Album album) {
        albumDAO.deleteAlbum(album);
    }

    @Override
    public void deleteAlbums(String[] id) {
        albumDAO.deleteAlbums(id);
    }

    @Override
    public void updateAlbum(Album album) {
        albumDAO.updateAlbum(album);
    }

    @Override
    public void updateAlbumSetCover(Album album) {
        albumDAO.updateAlbumSetCover(album);
    }

    @Override
    public void updateAlbumSetSeriesAndSize(Album album) {
        albumDAO.updateAlbumSetSeriesAndSize(album);
    }

    @Override
    public void updateAlbumSetScore(Album album) {
        albumDAO.updateAlbumSetScore(album);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Album> selectAlbumsAll() {
        List<Album> list = albumDAO.selectAlbumsAll();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectAlbumsPage(Integer page, Integer rows) {
        int start = (page - 1) * rows;
        Map<String, Object> map = new HashMap<>();
        Integer count = albumDAO.selectAlbumCount();
        long total = (count - 1) / rows + 1;
        List<Album> albums = albumDAO.selectAlbumsPage(start, rows);
        map.put("rows", albums);
        map.put("page", page);
        map.put("records", count);
        map.put("total", total);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectAlbumCount() {
        Integer count = albumDAO.selectAlbumCount();
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Album> selectAlbumsByNewstime() {
        List<Album> list = albumDAO.selectAlbumsByNewstime();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album selectAlbumById(String id) {
        Album album = albumDAO.selectAlbumById(id);
        return album;
    }
}
