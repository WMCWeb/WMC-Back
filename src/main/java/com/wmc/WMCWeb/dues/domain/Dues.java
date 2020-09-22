package com.wmc.WMCWeb.dues.domain;

public class Dues {
    private Long id;
    private Integer state;
    private Integer amount;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Integer getState(){
        return state;
    }

    public void setState(Integer state){
        this.state = state;
    }

    public Integer getAmount(){
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
