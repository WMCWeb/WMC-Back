package com.wmc.WMCWeb.notice.repository;

import com.wmc.WMCWeb.notice.entity.Notice;
import com.wmc.WMCWeb.notice.policy.SearchPolicy;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {

    boolean save(Notice notice);
    Optional<List<Notice>> findNotice(List<SearchPolicy> searchConditions);
    boolean editNotice(String regNo, Notice notice);

}
