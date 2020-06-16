package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Repository
public interface BannerDAO {
    //@Insert("insert into cmfz_banner values(#{id},#{title},#{describes},#{state},#{picture},#{time})")
    void insertBanner(Banner banner);

    //@Delete("delete from cmfz_banner where id = #{id}")
    void deleteBanner(Banner banner);

    void deleteBanners(String[] id);

    //@Update("update cmfz_banner set title = #{title},describe = #{describes},state = #{state},picture = #{picture},time = #{time} where id=#{id}")
    void updateBanner(Banner banner);

    //@Select("select id,title,describes,state,picture,time from cmfz_banner")
    List<Banner> selectBannersAll();//查询所有轮播图片

    //@Select("select id,title,describes,state,picture,time from cmfz_banner limit #{start},#{rows}")
    List<Banner> selectBannersPage(@Param(value = "start") int start, @Param(value = "rows") int rows);//查询所有轮播图片并分页

    //@Select("select count(id) from cmfz_banner")
    Integer selectBannerCount();//查询轮播图片的个数

    //@Select("select id,title,describes,state,picture,time from cmfz_banner where state = #{state}")
    List<Banner> selectBannersByState(String state);//根据状态查询轮播图片

    //@Select("select id,title,describes,state,picture,time from cmfz_banner where id = #{id}")
    Banner selectBannerById(String id);//根据id精确查找轮播图片
}
