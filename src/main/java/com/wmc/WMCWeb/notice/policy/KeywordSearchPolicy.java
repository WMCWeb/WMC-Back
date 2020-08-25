package com.wmc.WMCWeb.notice.policy;

import java.util.HashMap;
import java.util.Map;

public class KeywordSearchPolicy implements SearchPolicy{
    private Map<String, String> param;

    public KeywordSearchPolicy(String keyword) {
        this.param = new HashMap<>();
        this.param.put("keyword", keyword);
    }

    @Override
    public Map<String, String> getParam() {

        if(param.containsKey("keyword")) {
            return param;
        }
        else{
            return null;
        }
    }

}
