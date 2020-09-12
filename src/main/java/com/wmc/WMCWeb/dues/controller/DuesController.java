package com.wmc.WMCWeb.dues.controller;

import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.service.DuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dues")
public class DuesController {

    private final DuesService duesService;

    @Autowired
    public DuesController(DuesService duesService){
        this.duesService = duesService;
    }

    @GetMapping(value = "/new")
    public String createForm(){
        return "/dueses/createDuesForm.html";
    }

    @PostMapping(value = "/new")
    public String create(DuesForm form){
        Dues dues = new Dues();
        dues.setState(form.getState());
        dues.setAmount(form.getAmount());

        duesService.register(dues);

        return "redirect:/";
    }

    /**
     * 조회
     * @param Request Parameter (Select Contdition)
     * @param model
     * @return List Of Dues
     */
    @GetMapping
    public List<Dues> getDue(@RequestParam Map<String, String> param) {
        List<Dues> dues = duesService.findDues(param);
        return dues;
    }
}
