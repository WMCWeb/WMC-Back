package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
        // 2020.11.22 이경훈: regNo를 db기반으로 생성하는 방식으로 수정
        // @TODO: 테스트 필요
        dues.setRegId(getRegNo());
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
            // sql에서는 date로 받으려면 Date형태로 받아야 한다. LocalDate -> Date
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
     * 2020.1.22 이경훈
     *  reg_no 얻어오는 함수
     * @return : reg_no
     */
    public String getRegNo() {
        String result = "";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             CallableStatement cstmt = conn.prepareCall("{call sp_update_due_reg_no(?)}");
        ){
            cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            cstmt.executeQuery();
            result = cstmt.getString(1);
        }
        catch(SQLException se){
            logger.error("cannot update reg_no", se);
        }

        return result;
    }

    /**
     * @param param : request 파라미터
     * @return : 조회된 회비내역 리스트
     */
    // 2020.11.12 이경훈: 조회 개발 v1.0
    // @TODO: {1. 전체조회만 테스트 완료, 조건별로 테스트 필요}, {2. paging}
    @Override
    public List<Dues> findDue(Map<String, String> param){
        StringBuilder sql = new StringBuilder();
        List<Dues> result = new ArrayList<>();
        sql.append("SELECT * FROM due WHERE del = 'N'\n");

        String dateCode = param.get("dateCode");
        if("D".equals(dateCode)){
            // "시작일 + 마지막일"로  조회검색
            sql.append("    AND DATE_FORMAT(date, '%Y%m%d') BETWEEN ? AND ?\n");
        }
        else if("S".equals(dateCode)){
            // "년도 + 학기"로 검색
            sql.append("    AND semester = ?\n");
        }

        if(param.containsKey("keyword")){
            // 키워도 검색
            sql.append("    AND explanation LIKE CONCAT('%', ?, '%')\n");
        }

        if(param.containsKey("state")){
            // 수입/지출 검색
            sql.append("    AND state = ?\n");
        }

        if(param.containsKey("category")){
            // 카테고리 검색
            sql.append("    AND category = ?\n");
        }

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = ((Supplier<PreparedStatement>)() -> {
                 // supplier에서 PreparedStatement 파라미터 완성
                 try {
                     int idx = 1;
                     PreparedStatement s = conn.prepareStatement(sql.toString());
                     StringBuilder parameters = new StringBuilder("\n[Parameters: ");
                     if("D".equals(dateCode)) {
                         String startDate = param.get("startDate");
                         String endDate = param.get("endDate");
                         s.setString(idx++, startDate);
                         s.setString(idx++, endDate);
                         parameters.append(startDate + ", ");
                         parameters.append(endDate + ", ");
                     }
                     else if("S".equals(dateCode)) {
                         String semester = param.get("yearSemester");
                         s.setString(idx++, semester);
                         parameters.append(semester + ", ");
                     }

                     if(param.containsKey("keyword")){
                         // 키워드 검색
                         String keyword = param.get("keyword");
                         s.setString(idx++, keyword);
                         parameters.append(keyword + ", ");
                     }

                     if(param.containsKey("state")){
                         // 수입/지출 검색
                         String state = param.get("state");
                         s.setString(idx++, state);
                         parameters.append(state + ", ");
                     }

                     if(param.containsKey("category")){
                         // 카테고리 검색
                         String category = param.get("category");
                         s.setString(idx, category);
                         parameters.append(category + ", ");
                     }

                     // sql이랑 parameter 로깅
                     logger.info(sql.toString() + "\n" + parameters.toString());
                     return s;
                 }
                 catch (SQLException e) {
                     logger.error("cannot make PreparedStatement in findDue function");
                     throw new RuntimeException(e);
                 }
             }).get();
             ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    Dues temp = new Dues();
                    temp.setRegId(rs.getString("id"));
                    temp.setDate(rs.getDate("date").toLocalDate());
                   // temp.setDate(rs.getString("date"));
                    temp.setAmount(rs.getInt("amount"));
                    temp.setExplain(rs.getString("explanation"));
                    temp.setSemester(rs.getString("semester"));
                    temp.setState(rs.getString("state"));
                    temp.setBalance(rs.getInt("balance"));
                    temp.setDel("N");
                    result.add(temp);
                    logger.debug(temp.toString() + "\n");
                }
        } catch (Exception e) {
            logger.error("cannot select dues in findDue function");
            e.printStackTrace();
        }
        return result;
    }

}
