package com.wmc.WMCWeb.notice.repository;

import com.wmc.WMCWeb.notice.entity.Notice;
import com.wmc.WMCWeb.notice.policy.SearchPolicy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CacheNoticeRepository implements NoticeRepository{

    private static List<Notice> repository = new ArrayList<>();
    private static int sequence = 0;

    @Override
    public Optional<Notice> save(Notice notice) {
        if (notice == null){
            return Optional.empty();
        }
        repository.add(notice);
        return Optional.of(notice);
    }

    @Override
    public Optional<List<Notice>> findNotice(List<SearchPolicy> searchConditions) {
        /*
        @TODO:
            parameter 꺼내서 공지 조회후 반환
         */
        return Optional.empty();
    }

    public Optional<List<Notice>> findByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Notice> result = repository.stream()
                .filter(notice -> notice.getDateTime().isAfter(startDateTime)
                        && notice.getDateTime().isBefore(endDateTime)
                )
                .sorted((n1, n2) -> {
                    if (n1.getDateTime().isBefore(n2.getDateTime()))
                        return 1;
                    else if(n1.getDateTime().isAfter(n2.getDateTime()))
                        return -1;
                    else
                        return 0;
                })
                .collect(Collectors.toList());

        if(result.size() == 0)
            return Optional.empty();
        else
            return Optional.of(result);
    }
}
