package com.wmc.WMCWeb.notice.policy;

import java.util.HashMap;
import java.util.Map;

public class WriterSearchPolicy implements SearchPolicy{

    private Map<String, String> param;

    public WriterSearchPolicy(String writer) {
        this.param = new HashMap<>();
        this.param.put("writer", writer);
    }

    @Override
    public Map<String, String> getParam() {
        if(param.containsKey("writer")){
            return param;
        }
        else {
            return null;
        }
    }
}
