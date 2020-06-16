package com.baizhi.dao;

import com.baizhi.entity.Guru;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuruDAO {
    void insertGuru(Guru guru);

    void deleteGuru(Guru guru);

    void updateGuru(Guru guru);

    List<Guru> selectGurusAll();//查询所有上师

    List<Guru> selectGurusPage(@Param(value = "start") int start, @Param(value = "rows") int rows);//查询所有上师并分页

    Integer selectGuruCount();//查询上师的总个数

    Guru selectGuruById(String id);//根据id查询上师
}
