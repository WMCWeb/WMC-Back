package com.wmc.WMCWeb.dues.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wmc.WMCWeb.common.Response;
import com.wmc.WMCWeb.common.service.MemberService;
import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.repository.AwsMysqlDuesRepository;
import com.wmc.WMCWeb.dues.service.DuesService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dues")
public class DuesController {

    private static final Logger logger = LogManager.getLogger(AwsMysqlDuesRepository.class);
    private final DuesService duesService;
    private final MemberService memberService;

    @Autowired
    public DuesController(DuesService duesService, MemberService memberService){
        this.duesService = duesService;
        this.memberService = memberService;
    }

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
// @TODO: 필수파라미터누락된 케이스, 성공 케이스까지 테스트완료, --> DB연결 안되는 경우는 테스트 아직..
    @GetMapping("/new")
    public Map<String, String> createDue(@RequestParam Map<String, String> param, @RequestHeader("Auth-Key") String authKey) throws SQLException {
        Map<String, String> result = new HashMap<>();
        Gson gson = new Gson();
        Response info = new Response();

        if(!memberService.isValidKey(authKey)){
            // key가 유효하지 않은 경우
            logger.error("인증 키가 유효하지 않습니다.");
            result.put("regId", "");
            result.put("info", gson.toJson(info.getFail()));
        }
        else if(!param.containsKey("date") || !param.containsKey("amount") || !param.containsKey("category") ||
                !param.containsKey("semester") ||!param.containsKey("state")){
            // 필수파라미터 누락된 경우
            logger.error("필수 파라미터가 누락되었습니다.");
            result.put("regId", "");
            result.put("info", gson.toJson(info.getAbseentParameter()));
        }
        else {
            // 정상 처리 케이스
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
            try {
                // 성공한 경우
                result.put("regId", duesService.register(dues));
                result.put("info", gson.toJson(info.getSuccess()));

            }
            catch (SQLException se){
                // DB 에러
                logger.error(se.getMessage());
                result.put("regId", "");
                result.put("info", gson.toJson(info.getDBError()));
                return result;
            }
        }

        return result;
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
    public Map<String, String> getDue(@RequestParam Map<String, String> param, @RequestHeader("Auth-Key") String authKey) {

        Gson gson = new Gson();
        Response info = new Response();
        Map<String, String> resultMap = new HashMap<>();

        if(!memberService.isValidKey(authKey)){
            // key가 유효하지 않은 경우
            logger.error("인증 키가 유효하지 않습니다.");
            resultMap.put("data", "[]");
            resultMap.put("info", gson.toJson(info.getFail()));
        }
        else if(!param.containsKey("dateCode") || !param.containsKey("pageNo")) {
            // 필수 parameter 누락
            logger.error("필수 파라미터가 누락되었습니다.");
            resultMap.put("data", "[]");
            resultMap.put("info", gson.toJson(info.getAbseentParameter()));
        }
        else{// 정상처리 케이스
            try {
                List<Dues> dues = duesService.findDues(param);

                gson = new GsonBuilder().setPrettyPrinting().create();

                resultMap.put("data", gson.toJson(dues));
                resultMap.put("info", gson.toJson(info.getSuccess()));
            }
            catch(SQLException se){
                logger.error(se.getMessage());
                resultMap.put("data", "[]");
                resultMap.put("info", gson.toJson(info.getDBError()));

                return resultMap;
            }
        }

        return resultMap;
    }

    @DeleteMapping(value = "/{regId}")
    public Map<String, String> deleteDue(@PathVariable("regId") String regId, @RequestHeader("Auth-Key") String authKey) {
        Gson gson = new Gson();
        Response info = new Response();
        Map<String, String> resultMap = new HashMap<>();

        if(!memberService.isValidKey(authKey)){
            // key가 유효하지 않은 경우
            logger.error("인증 키가 유효하지 않습니다.");
            resultMap.put("result", "N");
            resultMap.put("info", gson.toJson(info.getFail()));
        }
        else{
            // 정상처리 케이스
            try {
                String resultMsg = duesService.deleteDue(regId);    // 성공시 Y, 실패시 N
                if("Y".equals(resultMsg)){
                    logger.debug(regId + ": 정상적으로 삭제되었습니다.");
                    resultMap.put("result", "Y");
                    resultMap.put("info", gson.toJson(info.getSuccess()));
                }
                else if("E".equals(resultMsg)){
                    logger.error("Cannot delete [" + regId + "]: Already Deleted");
                    resultMap.put("result", "N");
                    resultMap.put("info", gson.toJson(info.getAlreadyDeleted()));
                }
                else if("N".equals(resultMsg)){
                    logger.error("Cannot delete [" + regId + "]: Not Exists");
                    resultMap.put("result", "N");
                    resultMap.put("info", gson.toJson(info.getNotExists()));
                }
            }
            catch(SQLException se){
                logger.error(se.getMessage());
                resultMap.put("result", "N");
                resultMap.put("info", gson.toJson(info.getDBError()));
                return resultMap;
            }
        }
        return resultMap;
    }
}
