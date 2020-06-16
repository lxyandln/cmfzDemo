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
//@Document(indexName = "cmfz",type = "article")
public class Article {
    //@Id
    private String id;//编号
    //@Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;//标题
    //@Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String author;//作者
    //@Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;//内容
    //@Field(type = FieldType.Keyword)
    private String state;//状态
    //@Field(type = FieldType.Date)
    private Date time;//上传时间
}
