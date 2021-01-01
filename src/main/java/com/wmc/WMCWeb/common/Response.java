package com.wmc.WMCWeb.common;

public class Response {
    private String code;
    private String message;

    public Response getSuccess(){
        this.code = "S";
        this.message = "Success";
        return this;
    }

    public Response getAbseentParameter(){
        this.code = "B";
        this.message = "Absent Essential Parameter";
        return this;
    }

    public Response getDBError(){
        this.code = "D";
        this.message = "Cannot access DB";
        return this;
    }
}
