package com.baizhi.service;

import com.baizhi.dao.BannerDAO;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDAO bannerDAO;

    @Override
    public String insertBanner(Banner banner) {
        String id = UUID.randomUUID().toString();
        banner.setId(id).setTime(new Date());
        bannerDAO.insertBanner(banner);
        return id;
    }

    @Override
    public void deleteBanner(Banner banner) {
        bannerDAO.deleteBanner(banner);
    }

    @Override
    public void deleteBanners(String[] id) {
        bannerDAO.deleteBanners(id);
    }

    @Override
    public void updateBanner(Banner banner) {
        bannerDAO.updateBanner(banner);
    }

    @Override
    public List<Banner> selectBannersAll() {
        List<Banner> list = bannerDAO.selectBannersAll();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectBannersPage(Integer page, Integer rows) {
        int start = (page - 1) * rows;
        Map<String, Object> map = new HashMap<>();
        Integer count = bannerDAO.selectBannerCount();
        long total = (count - 1) / rows + 1;
        List<Banner> banners = bannerDAO.selectBannersPage(start, rows);
        map.put("rows", banners);//展示的数据
        map.put("page", page);//当前页码
        map.put("records", count);//总条数
        map.put("total", total);//总页数
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectBannerCount() {
        Integer count = bannerDAO.selectBannerCount();
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> selectBannersByState(String state) {
        List<Banner> list = bannerDAO.selectBannersByState(state);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Banner selectBannerById(String id) {
        Banner banner = bannerDAO.selectBannerById(id);
        return banner;
    }
}
