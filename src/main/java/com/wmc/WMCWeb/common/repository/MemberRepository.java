package com.wmc.WMCWeb.common.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    public boolean isValidKey(String key){
        return true;
    }

}
