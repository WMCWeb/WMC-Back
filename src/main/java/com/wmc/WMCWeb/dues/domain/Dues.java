package com.wmc.WMCWeb.dues.domain;

import java.util.Date;

/**
 * 2020.09.23 윤수빈
 * DUE 테이블에 맞게 class 수정
 * 등록번호, 날짜, 금액, 카테고리, 설명, 학기, 구분, 삭제여부, 잔액
 * @Todo : 카테고리 테이블 분류(?), 잔액 자동계산?
 * */
public class Dues {

    private Long regId;
    private Date date;
    private Integer amount;
    private String category;
    private String explain;
    private String semester;
    private String state;
    private String del;
    private Integer balance;

    public Long getRegId(){
        return regId;
    }
    public void setRegId(Long regId){
        this.regId = regId;
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date=date;
    }

    public Integer getAmount(){
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category=category;
    }

    public String getExplain(){
        return explain;
    }
    public void setExplain(String explain){
        this.explain=explain;
    }

    public String getSemester(){
        return semester;
    }
    public void setSemester(String semester){
        this.semester=semester;
    }

    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state = state;
    }

    public String getDel(){
        return del;
    }
    public void setDel(String del){
        this.del = del;
    }

    public Integer getBalance(){
        return balance;
    }
    public void setBalance(Integer balance){
        this.balance = balance;
    }
}
