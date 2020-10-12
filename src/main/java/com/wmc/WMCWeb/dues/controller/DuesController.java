package com.wmc.WMCWeb.dues.controller;

import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.repository.AwsMysqlDuesRepository;
import com.wmc.WMCWeb.dues.service.DuesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dues")
public class DuesController {

    private static final Logger logger = LogManager.getLogger(AwsMysqlDuesRepository.class);
    private final DuesService duesService;

    @Autowired
    public DuesController(DuesService duesService){
        this.duesService = duesService;
    }
/*
    @GetMapping(value = "/new")
    public String createForm(){
        return "/dueses/createDuesForm.html";
    }
*/

    @GetMapping("/new")
    public List<Dues> createDue(@RequestParam Map<String, String> param) {
        List<Dues> dues = duesService.findDues(param);
        String state = param.get("state");
        System.out.println("state : " + state);

        return dues;
    }


    @PostMapping(value = "/dues")
    public String create(DuesForm form, @RequestParam Map<String, String> param){
        Dues dues = new Dues();
        dues.setDate(form.getDate());
        dues.setState(form.getState());
        dues.setAmount(form.getAmount());

        duesService.register(dues);

        return "redirect:/";
    }

    /**
     * 회비내역 조회
     * @param param Request Parameter (조회 조건)
     * @return List Of Dues
     */

    @GetMapping
    public List<Dues> getDue(@RequestParam Map<String, String> param) {
        List<Dues> dues = duesService.findDues(param);


        return dues;
    }


}
