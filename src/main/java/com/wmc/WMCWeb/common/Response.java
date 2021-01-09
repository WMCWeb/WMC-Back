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

    public Response getFail(){
        this.code = "F";
        this.message = "Failed";
        return this;
    }

    public Response getAlreadyDeleted(){
        this.code = "E";
        this.message = "Already Deleted";
        return this;
    }

    public Response getNotExists(){
        this.code = "N";
        this.message = "Not Exists";
        return this;
    }
}
