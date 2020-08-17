package com.wmc.WMCWeb.notice.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WriterSearchPolicy implements SearchPolicy{

    private Map<String, String> param;

    public WriterSearchPolicy() {
        this.param = new HashMap<>();
    }

    @Override
    public void setParam(List<String> params) {
        param.put("writer", params.get(0));
    }

    @Override
    public Optional<Map<String, String>> getParam() {
        if(param.containsKey("writer")){
            return Optional.of(param);
        }
        else {
            return Optional.empty();
        }
    }
}
