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
        return duesRepository.save(dues);
    }

    /**
     * 조회 조건에 맞게 회비 내역 조회
     * @param param : Request에서 넘어온 Parameter
     * @return 조회된 Due 리스트
     */
    public List<Dues> findDues(Map<String, String> param) throws SQLException {
        return duesRepository.findDue(param);
    }

    /**
     * 회비 내역 삭제
     * @param regId : 삭제할 회비 내역
     * @return
     * @throws SQLException
     */
    public String deleteDue(String regId) throws SQLException{
        if(!duesRepository.isExists(regId)){
            return "N";
        }
        if (duesRepository.isDeleted(regId)) {
            return "E";
        }
        //@TODO: 정상처리시 Y리턴.
        return null;
    }
}
