package com.wmc.WMCWeb.notice.controller;

import com.wmc.WMCWeb.notice.entity.Notice;
import com.wmc.WMCWeb.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public List<Notice> getNotice(@RequestParam Map<String, String> param){
        List<Notice> result = noticeService.findNotice(param);
        return result;
    }
}
