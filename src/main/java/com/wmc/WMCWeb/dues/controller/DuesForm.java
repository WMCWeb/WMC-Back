package com.wmc.WMCWeb.dues.controller;

public class DuesForm {

    private String state;
    private Integer amount;

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
