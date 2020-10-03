package com.wmc.WMCWeb.dues.service;

import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.repository.DuesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DuesService {
    private final DuesRepository duesRepository;

    public DuesService(DuesRepository duesRepository){
        this.duesRepository=duesRepository;
    }

    /*
    회비내역 등록
     */
    public Long register(Dues dues){
        duesRepository.save(dues);
        return dues.getRegId();
    }

    /**
     * 조회 조건에 맞게 회비 내역 조회
     * @param Request에서 넘어온 Parameter
     * @return 조회된 Due 리스트
     */
    public List<Dues> findDues(Map<String, String> param){
        return duesRepository.findDue(param);
    }

}
