package com.baizhi.service;

import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    void insertGuru(Guru guru);

    void deleteGuru(Guru guru);

    void updateGuru(Guru guru);

    List<Guru> selectGurusAll();//查询所有上师

    Map<String, Object> selectGurusPage(Integer page, Integer rows);//查询所有上师并分页

    Integer selectGuruCount();//查询上师的总个数

    Guru selectGuruById(String id);//根据id查询上师
}
