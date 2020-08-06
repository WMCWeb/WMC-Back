package com.wmc.WMCWeb.notice.repository;

import com.wmc.WMCWeb.notice.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NoticeRepository {

    Optional<Notice> save(Notice notice);
    Optional<List<Notice>> findByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime);
    Optional<List<Notice>> findByWriter(String Writer);
    Optional<List<Notice>> findByKeyWord(String KeyWord);
    Optional<List<Notice>> findAll();

}
