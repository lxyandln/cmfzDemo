package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Banner {
    @Excel(name = "编号")
    private String id;//编号
    @Excel(name = "标题")
    private String title;//标题
    @Excel(name = "描述")
    private String describes;//描述
    @Excel(name = "状态", replace = {"展示_1", "隐藏_2"})
    private String state;//状态（展示/不展示）
    @Excel(name = "图片", type = 2)
    private String picture;//图片
    @Excel(name = "上传时间", format = "yyyy-MM-dd")
    private Date time;//上传时间
}
