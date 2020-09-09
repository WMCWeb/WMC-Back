package com.wmc.WMCWeb.dues.service;

import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.repository.DuesRepository;

import java.util.List;
import java.util.Optional;

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
        return dues.getId();
    }

    /*
    전체 회비 내역 조회
     */
    public List<Dues> findDues(){
        return duesRepository.findAll();
    }

    public Optional<Dues> findOne(Long duesID){
        return duesRepository.findById(duesID);
    }


}
