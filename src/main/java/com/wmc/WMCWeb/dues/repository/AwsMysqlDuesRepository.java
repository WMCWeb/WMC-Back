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
    @Value("${spring.datasource.test-url}")
    String URL;

    @Value("${spring.datasource.username}")
    String USERNAME;

    @Value("${spring.datasource.password}")
    String PASSWORD;

    /**
     * 2020.10.08. 윤수빈 : DUE 테이블에 Insert 시도
     * 2020.11.06. 윤수빈 : INSERT 성공!
     * @return
     */
    private static long sequence = 0L;
    @Override
    public Dues save(Dues dues) throws SQLException {
        dues.setRegId(String.valueOf(++sequence));
        //String query = "insert into due(id,date,amount,category,explanation,semester,state,del,balance) values(?,?,?,?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
             conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
              //ResultSet rs = pstm.executeQuery();
            pstm = conn.prepareStatement("insert into due(id,date,amount,category,explanation,semester,state,del,balance) values(?,?,?,?,?,?,?,?,?)"
            ,Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1,dues.getRegId());
            pstm.setDate(2, Date.valueOf(dues.getDate()));
            pstm.setInt(3,dues.getAmount());
            pstm.setString(4,dues.getCategory());
            pstm.setString(5,dues.getExplain());
            pstm.setString(6,dues.getSemester());
            pstm.setString(7,dues.getState());
            pstm.setString(8,dues.getDel());
            pstm.setInt(9,Integer.valueOf(dues.getBalance()));

            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();

            if(rs.next()){
                dues.setRegId(rs.getString(1));
            }

        }
        catch(Exception e) {
            logger.error("cannot execute query", e);
            e.printStackTrace();
        }
        finally {
            if(rs != null) try {rs.close();} catch (Exception e2){}
            if(pstm != null) try {pstm.close();} catch (Exception e2){}
            if(conn != null) try {conn.close();} catch (Exception e2){}
        }

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
        String query = "select * from category where code = 'B'";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
        )
        {
            while(rs.next()){
                temp.setCategory(rs.getString("name"));
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
