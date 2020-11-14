package com.wmc.WMCWeb.dues.controller;

import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.repository.AwsMysqlDuesRepository;
import com.wmc.WMCWeb.dues.service.DuesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
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
    /**
     * 회비내역 저장
     * 2020.10.31. : param 읽어오기 시도
     * 2020.11.07. : param 읽어오기 성공
     * TO DO : 1. regID
     * 2. date 데이터타입 Date로 할 것
     * 3. del : 디폴트값 N으로 주기
     * 4. balance : 자동으로 계산 되게 하기
     * @return Dues
     */

    @GetMapping("/new")
    public Dues createDue(@RequestParam Map<String, String> param) throws SQLException {
        Dues dues = new Dues();
        String date = param.get("date");
        dues.setDate(date);
        Integer amount = Integer.valueOf(param.get("amount"));
        dues.setAmount(amount);
        String category = param.get("category");
        dues.setCategory(category);
        String explain = param.get("explain");
        dues.setExplain(explain);
        String semester = param.get("semester");
        dues.setSemester(semester);
        String state = param.get("state");
        dues.setState(state);
        String del = param.get("del");
        dues.setDel(del);
        Integer balance = Integer.valueOf(param.get("balance"));
        dues.setBalance(balance);

        //System.out.println(amount);
        duesService.register(dues);
        return dues;
        //http://localhost:8080/dues/news?date=20201003?amount=10000?category=B?explain=test?semester=2020-1?state=i?del=Y?balance=100
    }

/*
    @PostMapping(value = "/dues")
    public String create(DuesForm form, @RequestParam Map<String, String> param){
        Dues dues = new Dues();
        dues.setDate(form.getDate());
        dues.setState(form.getState());
        dues.setAmount(form.getAmount());

        duesService.register(dues);

        return "redirect:/";
    }
*/
    /**
     * 회비내역 조회
     * @param param Request Parameter (조회 조건)
     * @return List Of Dues
     */
    @GetMapping
    public List<Dues> getDue(@RequestParam Map<String, String> param) {
        List<Dues> dues = null;
        if(param.containsKey("dateCode") && param.containsKey("pageNo")) {
            dues = duesService.findDues(param);
        }
        else{
            // 필수 parameter 누락
            logger.error("dateCode와 pageNo는 필수 파라미터 입니다.");
        }
        return dues;
    }


}
