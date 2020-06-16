package com.baizhi.controller;

import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/guru")
public class GuruController {
    @Autowired
    private GuruService guruService;

    @ResponseBody
    @RequestMapping("/selectGurusPage")
    public Map<String, Object> selectGurusPage(Integer page, Integer rows) {
        Map<String, Object> map = guruService.selectGurusPage(page, rows);
        /*Map<String, Object> map = new HashMap<>();
        Integer count = guruService.selectGuruCount();
        long total = (count-1)/rows+1;
        List<Guru> gurus = guruService.selectGurusPage(page, rows);
        map.put("rows",gurus);
        map.put("page",page);
        map.put("records",count);
        map.put("total",total);*/
        return map;
    }

    @ResponseBody
    @RequestMapping("edit")
    public void edit(Guru guru, String oper) {
        if (oper.equals("add")) {
            guruService.insertGuru(guru);
        } else if (oper.equals("edit")) {
            guruService.updateGuru(guru);
        } else if (oper.equals("del")) {
            guruService.deleteGuru(guru);
        }
    }
}
