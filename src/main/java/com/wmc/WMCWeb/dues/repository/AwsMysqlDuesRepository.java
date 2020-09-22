package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

    @Autowired
    ApplicationContext ctx;

    private String URL;
    private String USERNAME;
    private String PASSWORD;


    @Override
    public Dues save(Dues dues) {
        return null;
    }

    @Override
    public Optional<Dues> findById(Long id) {
        return Optional.empty();
    }

    /**
     * 2020.09.21 이경훈:
     *  일단 임시로 aws ec2 인스턴스 만들어서 구축한 mysql db에 붙는것 까지 확인함
     * @TODO: connection 모듈화 필요
     * @return
     */
    @Override
    public List<Dues> findAll(Map<String, String> param){
        Environment env = ctx.getEnvironment();
        URL = env.getProperty("spring.datasource.url");
        USERNAME = env.getProperty("spring.datasource.username");
        PASSWORD = env.getProperty("spring.datasource.password");


        String query = "SELECT * FROM test";
        try (Connection conn = DriverManager.getConnection(URL,
                USERNAME,
                PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
        )
        {
            while(rs.next()){
                System.out.println("DEBUG::" + rs.getString("ID"));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Dues> findByState(Integer state) {
        return Optional.empty();
    }
}
