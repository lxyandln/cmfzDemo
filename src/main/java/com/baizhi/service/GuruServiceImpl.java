package com.baizhi.service;

import com.baizhi.dao.GuruDAO;
import com.baizhi.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDAO guruDAO;

    @Override
    public void insertGuru(Guru guru) {
        guru.setId(UUID.randomUUID().toString())
                .setTime(new Date());
        guruDAO.insertGuru(guru);
    }

    @Override
    public void deleteGuru(Guru guru) {
        guruDAO.deleteGuru(guru);
    }

    @Override
    public void updateGuru(Guru guru) {
        guruDAO.updateGuru(guru);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Guru> selectGurusAll() {
        List<Guru> list = guruDAO.selectGurusAll();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectGurusPage(Integer page, Integer rows) {
        int start = (page - 1) * rows;
        Map<String, Object> map = new HashMap<>();
        Integer count = guruDAO.selectGuruCount();
        long total = (count - 1) / rows + 1;
        List<Guru> gurus = guruDAO.selectGurusPage(start, rows);
        map.put("rows", gurus);
        map.put("page", page);
        map.put("records", count);
        map.put("total", total);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectGuruCount() {
        Integer count = guruDAO.selectGuruCount();
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Guru selectGuruById(String id) {
        Guru guru = guruDAO.selectGuruById(id);
        return guru;
    }
}
