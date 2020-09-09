package com.wmc.WMCWeb.dues.controller;

import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.service.DuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DuesController {

    private final DuesService duesService;

    @Autowired
    public DuesController(DuesService duesService){
        this.duesService = duesService;
    }

    @GetMapping(value= "/dueses/new")
    public String createForm(){
        return "/dueses/createDuesForm.html";
    }

    @PostMapping(value = "/dueses/new")
    public String create(DuesForm form){
        Dues dues = new Dues();
        dues.setState(form.getState());
        dues.setAmount(form.getAmount());

        duesService.register(dues);

        return "redirect:/";
    }

    @GetMapping(value = "/dueses")
    public String list(Model model){
        List<Dues> dues = duesService.findDues();
        model.addAttribute("/dueses", dues);
        return "/dueses/duesList";

    }

}
