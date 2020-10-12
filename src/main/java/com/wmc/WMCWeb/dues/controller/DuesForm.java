package com.wmc.WMCWeb.dues.controller;

import java.util.Date;

public class DuesForm {

    private Date date;
    private Integer amount;
    private String category;
    private String explain;
    private String semester;
    private String state;
    private String del;
    private Integer balance;

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public Integer getAmount(){
        return amount;
    }

    public void setAmount(Integer amount){
        this.amount = amount;
    }


}
