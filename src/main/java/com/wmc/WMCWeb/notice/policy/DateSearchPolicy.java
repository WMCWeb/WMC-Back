package com.wmc.WMCWeb.notice.policy;

import java.util.HashMap;
import java.util.Map;

public class DateSearchPolicy implements SearchPolicy{

    private Map<String, String> param;

    public DateSearchPolicy(String start, String end) {
        this.param = new HashMap<>();
        this.param.put("start", start);
        this.param.put("end", end);
    }

    /**
     * @return { "start" : 시작날짜, "end" : 끝날짜 }
     */
    @Override
    public Map<String, String> getParam() {
        if(param.containsKey("start") && param.containsKey("end")){
            return param;
        }
        else {
            return null;
        }
    }
}
