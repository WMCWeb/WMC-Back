package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

// 2020.09.20 이경훈: Repository 애노테이션 추가
@Repository
public interface DuesRepository {
    String save(Dues dues) throws SQLException;

    // Dues save(Dues dues);
    List<Dues> findDue(Map<String, String> param) throws SQLException;

}
