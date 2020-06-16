package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDAO {
    //@Select("select * from cmfz_admin where username = #{username} and password = #{password}")
    Admin selectAdminByUsernameAndPassword(@Param(value = "username") String username, @Param(value = "password") String password);

    @Select("select * from cmfz_admin where username = #{username}")
    Admin selectAdminByUsername(String username);
}
