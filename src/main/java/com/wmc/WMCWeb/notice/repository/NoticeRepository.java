package com.wmc.WMCWeb.notice.repository;

import com.wmc.WMCWeb.notice.entity.Notice;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NoticeRepository {

    Optional<Notice> save(Notice notice);
    Optional<List<Notice>> findNotice(Map param);

}
