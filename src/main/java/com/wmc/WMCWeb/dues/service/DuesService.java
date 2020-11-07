package com.wmc.WMCWeb.dues.service;

import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.repository.AwsMysqlDuesRepository;
import com.wmc.WMCWeb.dues.repository.DuesRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class DuesService {

    private static final Logger logger = LogManager.getLogger(DuesService.class);
    private final AwsMysqlDuesRepository duesRepository;

    public DuesService(AwsMysqlDuesRepository duesRepository){
        this.duesRepository=duesRepository;
    }

    /**
    회비내역 등록
     */
    public String register(Dues dues) throws SQLException {
        duesRepository.save(dues);
        return dues.getRegId();
    }

    /**
     * 조회 조건에 맞게 회비 내역 조회
     * @param param : Request에서 넘어온 Parameter
     * @return 조회된 Due 리스트
     */
    public List<Dues> findDues(Map<String, String> param){
        return duesRepository.findDue(param);
    }

}
