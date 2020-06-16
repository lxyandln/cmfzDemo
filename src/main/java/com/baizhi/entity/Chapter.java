package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Chapter {//章节
    private String id;//编号
    private String name;//标题
    private String content;//内容
    private double size;//大小（单位：M）
    private int duration;//时长（秒）
    private String albumid;//专辑id
}
