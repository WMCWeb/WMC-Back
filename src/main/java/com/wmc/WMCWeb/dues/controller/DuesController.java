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
/// 201108 최혜린 sql 어디있는지 몰라서 넣어놨어요 나중에 지워주세요
import org.springframework.beans.factory.annotation.Value;
import java.sql.*;
import java.util.ArrayList;
///
@RestController
@RequestMapping("/dues")
public class DuesController {
    ///201108 최혜린 sql 어디있는지 몰라서 넣어놨어요 나중에 지워주세요

    @Value("${spring.datasource.test-url}")
    String URL;

    @Value("${spring.datasource.username}")
    String USERNAME;

    @Value("${spring.datasource.password}")
    String PASSWORD;
    ///

    private static final Logger logger = LogManager.getLogger(AwsMysqlDuesRepository.class);
    private final DuesService duesService;

    @Autowired
    public DuesController(DuesService duesService) {
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
    public String create(DuesForm form, @RequestParam Map<String, String> param) {
        Dues dues = new Dues();
        dues.setDate(form.getDate());
        dues.setState(form.getState());
        dues.setAmount(form.getAmount());

        duesService.register(dues);

        return "redirect:/";
    }

    /**
     * 회비내역 조회
     *
     * @param param Request Parameter (조회 조건)
     * @return List Of Dues
     */


    @GetMapping
    public List<Dues> getDue(@RequestParam Map<String, String> param) {
        System.out.println(param);
        List<Dues> dues = duesService.findDues(param);
        System.out.println(param.get("state"));
        if (param.get("dateCode").equals("D")) {
            System.out.println("Hi");
        }
        if ((param.get("dateCode").equals("D")) || param.get("dateCode").equals("S")) {
            if (param.get("dateCode").equals("D")) {
                String startDate_first = param.get("startDate");
                String endDate_first = param.get("endDate");
                String startDate = startDate_first.substring(0, 4) + '-' + startDate_first.substring(4, 6) + '-' + startDate_first.substring(6, 8);
                String endDate = endDate_first.substring(0, 4) + '-' + endDate_first.substring(4, 6) + '-' + endDate_first.substring(6, 8);
                String query = "select * from due WHERE date BETWEEN '" + startDate + "' AND '" + endDate + "'";
                System.out.println(query);
//sql 실행 (?)

                try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query);
                ) {
                    while (rs.next()) {
                        System.out.println("2");
                        System.out.println(rs.getString("date"));
                        System.out.println(rs.getString("date"));
         //               dues.setCategory(rs.getString("name"));


                        System.out.println(rs);
//                temp.setCategory(rs.getString("name"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ArrayList<Dues> res = new ArrayList<>();
                //res.add(temp);
                //return res;
            }


        } else if (param.get("dateCode").equals("S")) {

        } else {
            System.out.println("D OR S 가 아닙니다.");
            return dues;
        }
        return dues;
    }
}
