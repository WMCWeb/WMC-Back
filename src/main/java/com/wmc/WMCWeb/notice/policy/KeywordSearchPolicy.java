package com.wmc.WMCWeb.notice.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KeywordSearchPolicy implements SearchPolicy{
    private Map<String, String> param;

    public KeywordSearchPolicy() {
        this.param = new HashMap<>();
    }

    @Override
    public void setParam(List<String> params) {
        param.put("keyword", params.get(0));
    }

    @Override
    public Optional<Map<String, String>> getParam() {

        if(param.containsKey("keyword")) {
            return Optional.of(param);
        }
        else{
            return Optional.empty();
        }
    }

}
