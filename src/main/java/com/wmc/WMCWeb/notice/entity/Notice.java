package com.wmc.WMCWeb.notice.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notice {

    private LocalDateTime dateTime;
    private String registNo;
    private String title;
    private String content;
    private String writer;
    private int views;

    public Notice(LocalDateTime dateTime, String registNo, String title, String content, String writer, int views) {
        this.dateTime = dateTime;
        this.registNo = registNo;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.views = views;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getRegistNo() {
        return registNo;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public int getViews() {
        return views;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("title: " + title+ "\n");
        sb.append("content: " + content+ "\n");
        sb.append("LocalDateTime: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
        sb.append("writer: " + writer+ "\n");
        return sb.toString();
    }
}
