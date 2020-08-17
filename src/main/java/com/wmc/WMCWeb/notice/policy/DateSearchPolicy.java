package com.wmc.WMCWeb.notice.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DateSearchPolicy implements SearchPolicy{

    private Map<String, String> param;

    public DateSearchPolicy() {
        this.param = new HashMap<>();
    }

    /**
     * @param params - [시작날짜, 끝날짜]
     */
    @Override
    public void setParam(List<String> params) {
        param.put("start", params.get(0));
        param.put("end", params.get(1));
    }

    @Override
    public Optional<Map<String, String>> getParam() {
        if(param.containsKey("start") && param.containsKey("end")){
            return Optional.of(param);
        }
        else {
            return Optional.empty();
        }
    }
}
