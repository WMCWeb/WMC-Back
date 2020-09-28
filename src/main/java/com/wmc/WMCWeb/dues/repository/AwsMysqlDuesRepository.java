package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 2020.09.20
 * 이경훈
 * AWS EC2 인스턴스에 구축된 MYSQL DB로 붙는 DAO 클래스
 */
@Repository
@Primary
public class AwsMysqlDuesRepository implements DuesRepository {

    // 2020.09.28 이경훈: logger
    private static final Logger logger = LogManager.getLogger(AwsMysqlDuesRepository.class);

    // 2020.09.28 이경훈: connection 생에 필요성한 정보들
    @Value("${spring.datasource.url}")
    String URL;

    @Value("${spring.datasource.username}")
    String USERNAME;

    @Value("${spring.datasource.password}")
    String PASSWORD;

    @Override
    public Dues save(Dues dues) {
        return null;
    }

    /**
     *
     * @return
     */
    // 2020.09.21 이경훈: 일단 임시로 aws ec2 인스턴스 만들어서 구축한 mysql db에 붙는것 까지 확인함
    @Override
    public List<Dues> findDue(Map<String, String> param){

        Dues temp = new Dues();
        String query = "select * from category where cate_code = 'B'";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
        )
        {
            while(rs.next()){
                temp.setCategory(rs.getString("cate_name"));
            }
        }
        catch(Exception e) {
            logger.error("cannot execute query", e);
            e.printStackTrace();
        }
        ArrayList<Dues> res = new ArrayList<>();
        res.add(temp);
        return res;
    }

}
