package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Album {//专辑
    private String id;//编号
    private String title;//标题
    private String author;//作者（上师）
    private String announcer;//播音员
    private int series;//集数
    private String brief;//简介
    private double size;//大小（M）
    private String cover;//封面
    private double score;//评分
    private Date newstime;//发布时间
    private String state;//状态
}
