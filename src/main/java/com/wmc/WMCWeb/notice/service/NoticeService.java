package com.wmc.WMCWeb.notice.service;

import com.wmc.WMCWeb.notice.entity.Notice;
import com.wmc.WMCWeb.notice.policy.DateSearchPolicy;
import com.wmc.WMCWeb.notice.policy.KeywordSearchPolicy;
import com.wmc.WMCWeb.notice.policy.SearchPolicy;
import com.wmc.WMCWeb.notice.policy.WriterSearchPolicy;
import com.wmc.WMCWeb.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }


    /**
     * Request에서 받은 Parameter에서 조회조건 분석해서 조회
     * @param param: Request parameter
     * @return  조회된 공지사항 리스트 return
     *          조회된 데이터가 없을 경우 null return
     */
    public List<Notice> findNotice(Map<String, String> param) throws IllegalStateException{
        if(!checkSearchParameter(param)){
            // 파라미터 에러
            throw new IllegalStateException("ERROR: Parameter is invalid.");
        }

        List<SearchPolicy> searchConditions = parseParam(param);
        Optional<List<Notice>> selectedResult = noticeRepository.findNotice(searchConditions);

        return selectedResult.orElse(null);
    }

    /**
     * 파라미터가 제대로 됐는지 (시작날짜만 있거나 마지막날짜만 있는지) 판단
     * @param param : Request Parameter
     * @return 정상적인 파라미터인지 여부
     */
    public boolean checkSearchParameter(Map<String, String> param){
        // 날짜조건 잘못된 경우 false return
        if((param.containsKey("start") && !param.containsKey("end"))
                || (param.containsKey("end") && !param.containsKey("start"))){
            return false;
        }
        return true;
    }

    /**
     * request에서 받은 파라미터 파싱해서 검색정책 List 생성
     * @param param: 검색 조건 parameter
     * @return : 검색조건 parameter를 분석해서 만든 SearchPolicy List
     */
    public List<SearchPolicy> parseParam(Map<String, String> param){
        List<SearchPolicy> searchConditions = new ArrayList<>();
        if(param.containsKey("writer")){
            searchConditions.add(new WriterSearchPolicy(param.get("writer")));
        }
        if(param.containsKey("keyword")){
            searchConditions.add(new KeywordSearchPolicy(param.get("keyword")));
        }
        if(param.containsKey("start") && param.containsKey("end")){
            searchConditions.add(new DateSearchPolicy(param.get("start"), param.get("end")));
        }
        return searchConditions;
    }

    public boolean editNotice(String regNo, Map<String, String> param){
        Notice target = null;
        noticeRepository.editNotice(regNo, target);
        return true;
    }
}
