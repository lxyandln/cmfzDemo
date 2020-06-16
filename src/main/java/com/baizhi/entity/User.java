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
public class User {
    private String id;//编号
    private String name;//姓名
    private String sign;//法号
    private String phone;//电话
    private String password;//密码
    private String head;//头像
    private String sex;//性别
    private String signature;//个性签名
    private String province;//省
    private String city;//市
    private String state;//状态
    private Date logtime;//最后登录时间
    private Date regtime;//注册时间
    private Integer num;//数量
}
