package com.wmc.WMCWeb.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * 2020.09.23 이경훈
 * DB Connection 생성을 위한 클래스
 */
@Component
public class JDBCManager{

    @Value("${spring.datasource.url}")
    String URL;

    @Value("${spring.datasource.username}")
    String USERNAME;

    @Value("${spring.datasource.password}")
    String PASSWORD;

    /**
     * DB커넥션 생성
     * @return DB Connection To Test DB
     * @throws SQLException
     */
    public Connection getTestDBConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
