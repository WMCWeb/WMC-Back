package com.wmc.WMCWeb.notice.policy;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SearchPolicy {

    void setParam(List<String> params);
    Optional<Map<String, String>> getParam();

}
