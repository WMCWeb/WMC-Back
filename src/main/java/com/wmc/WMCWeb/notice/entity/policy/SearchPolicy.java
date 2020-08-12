package com.wmc.WMCWeb.notice.entity.policy;

import java.util.*;

public class SearchPolicy {

    private Set<PolicyType> policy;
    private Map<String, String> param;

    public SearchPolicy() {
        this.policy = new HashSet<>();
        this.param = new HashMap<>();
    }

    public void addKeywordPolicy(String keyword){
        policy.add(PolicyType.KEYWORD);
        param.put("keyword", keyword);
    }

    public void addWriterPolicy(String writer){
        policy.add(PolicyType.KEYWORD);
        param.put("writer", writer);
    }

    public void addDatePolicy(String start, String end){
        policy.add(PolicyType.DATE);
        param.put("end", end);
        param.put("start", start);
    }

    public boolean containsKeyword(){
        return policy.contains(PolicyType.KEYWORD);
    }

    public boolean containsWriter(){
        return policy.contains(PolicyType.WRITER);
    }

    public boolean containsDate(){
        return policy.contains(PolicyType.DATE);
    }

    public String getKeyword() throws IllegalStateException{
        if(policy.contains(PolicyType.KEYWORD))
            return param.get("keyword");
        else
            throw new IllegalStateException("error: 키워드 검색 없음");
    }

    public String getWriter() throws IllegalStateException{
        if(policy.contains(PolicyType.WRITER))
            return param.get("writer");
        else
            throw new IllegalStateException("error: 작성자 검색 없음");
    }

    public List<String> getDate() throws IllegalStateException{
        if(policy.contains(PolicyType.DATE)) {
            List<String> result = new ArrayList<>();
            result.add(param.get("start"));
            result.add(param.get("end"));
            return result;
        }
        else
            throw new IllegalStateException("error: 작성자 검색 없음");
    }
}
