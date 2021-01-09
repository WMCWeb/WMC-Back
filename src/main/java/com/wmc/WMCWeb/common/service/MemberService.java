package com.wmc.WMCWeb.common.service;

import com.wmc.WMCWeb.common.repository.MemberRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private static final Logger logger = LogManager.getLogger(MemberService.class);
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public boolean isValidKey(String key){
        logger.debug("Check if the key is valid");
        return memberRepository.isValidKey(key);
    }

}
