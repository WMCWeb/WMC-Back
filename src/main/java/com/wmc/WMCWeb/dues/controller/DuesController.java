package com.wmc.WMCWeb.dues.controller;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

import com.wmc.WMCWeb.dues.service.DuesService;

@Controller
public class DuesController {

    @Resource(name="DuesService")
    private DuesService DuesService;
}
