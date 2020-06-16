package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    String insertBanner(Banner banner);

    void deleteBanner(Banner banner);

    void deleteBanners(String[] id);

    void updateBanner(Banner banner);

    List<Banner> selectBannersAll();//查询所有轮播图片

    Map<String, Object> selectBannersPage(Integer page, Integer rows);//查询所有轮播图片并分页

    Integer selectBannerCount();//查询轮播图片的个数

    List<Banner> selectBannersByState(String state);//根据状态查询轮播图片

    Banner selectBannerById(String id);//根据id精确查找轮播图片
}
