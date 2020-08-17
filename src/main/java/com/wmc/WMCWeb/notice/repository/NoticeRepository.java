package com.wmc.WMCWeb.notice.repository;

import com.wmc.WMCWeb.notice.entity.Notice;
import com.wmc.WMCWeb.notice.policy.SearchPolicy;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {

    Optional<Notice> save(Notice notice);
    Optional<List<Notice>> findNotice(List<SearchPolicy> searchConditions);

}
