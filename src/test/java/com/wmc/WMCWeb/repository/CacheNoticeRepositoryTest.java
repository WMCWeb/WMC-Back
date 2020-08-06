package com.wmc.WMCWeb.repository;


import com.wmc.WMCWeb.notice.entity.Notice;
import com.wmc.WMCWeb.notice.repository.CacheNoticeRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class CacheNoticeRepositoryTest {

    CacheNoticeRepository repository = new CacheNoticeRepository();

    @Test
    public void save(){
        Notice notice = new Notice(LocalDateTime.now(), "20200806-001", "test notice", "test notice contents", "leekh", 0);

        Notice result = repository.save(notice).get();
        System.out.println(result);

    }
}
