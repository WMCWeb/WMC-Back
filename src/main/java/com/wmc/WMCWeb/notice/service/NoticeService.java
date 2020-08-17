package com.wmc.WMCWeb.notice.service;

import com.wmc.WMCWeb.notice.entity.Notice;
import com.wmc.WMCWeb.notice.policy.SearchPolicy;
import com.wmc.WMCWeb.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    /**
     * Request에서 받은 Parameter에서 조회조건 분석해서 조회
     * @param param: Request parameter
     * @return 조회된 공지사항 리스트
     */
    public List<Notice> findNotice(Map<String, String> param){
        List<Notice> result = null;
        Optional<List<Notice>> selectedResult;

        /*
        @TODO:
            1. parameter 분리해서 검색 조건에 맞는 SearchPolicy 리스트 생성
            2. repository에 SearchPolicy list 전달해서 공지리스트 조회
         */
        List<SearchPolicy> searchConditions = new ArrayList<>();
        selectedResult = noticeRepository.findNotice(searchConditions);
        return selectedResult.orElse(null);
    }
}
