package com.wmc.WMCWeb.notice.repository;

import com.wmc.WMCWeb.notice.entity.Notice;
import com.wmc.WMCWeb.notice.policy.DateSearchPolicy;
import com.wmc.WMCWeb.notice.policy.KeywordSearchPolicy;
import com.wmc.WMCWeb.notice.policy.SearchPolicy;
import com.wmc.WMCWeb.notice.policy.WriterSearchPolicy;
import org.springframework.stereotype.Repository;

import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class CacheNoticeRepository implements NoticeRepository{

    private static List<Notice> repository = new ArrayList<>();

    @Override
    public boolean save(Notice notice) {

        if (notice == null){
            return false;
        }
        repository.add(notice);
        return true;
    }

    @Override
    public Optional<List<Notice>> findNotice(List<SearchPolicy> searchConditions) throws IllegalStateException {

        List<Notice> result = repository.stream()
                .filter(notice -> {
                    // 날짜로 필터링
                    List<SearchPolicy> dateSearchPolicy = searchConditions.stream()
                            .filter(searchPolicy -> searchPolicy instanceof DateSearchPolicy)
                            .collect(Collectors.toList());

                    if(dateSearchPolicy.size() == 1) {
                        Map<String, String> param = dateSearchPolicy.get(0).getParam()
                                .orElseThrow(() -> new IllegalStateException("ERROR: cannot get parameters."));

                        String startDateString = param.get("start");
                        String eneDateString = param.get("end");

                        // convert yyyyMMdd String to LocalDateTime instance
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDateTime startDate = LocalDateTime.parse(startDateString, dateTimeFormatter);
                        LocalDateTime endDate = LocalDateTime.parse(eneDateString, dateTimeFormatter);

                        return notice.getDateTime().isAfter(startDate)
                                && notice.getDateTime().isBefore(endDate);
                    }
                    else
                        return false;

                })
                .filter(notice -> {
                    // 키워드로 필터링
                    List<SearchPolicy> keywordSearchPolicy = searchConditions.stream()
                            .filter(searchPolicy -> searchPolicy instanceof KeywordSearchPolicy)
                            .collect(Collectors.toList());

                    if(keywordSearchPolicy.size() == 1){
                        Map<String, String> param = keywordSearchPolicy.get(0).getParam()
                                .orElseThrow(() -> new IllegalStateException("ERROR: cannot get parameters."));
                        String keyword = param.get("keyword");
                        return notice.getContent().contains(keyword)
                                || notice.getTitle().contains(keyword);
                    }
                    else
                        return false;

                })
                .filter(notice -> {
                    // 작성자로 필터링
                    List<SearchPolicy> writerSearchPolicy = searchConditions.stream()
                            .filter(searchPolicy -> searchPolicy instanceof WriterSearchPolicy)
                            .collect(Collectors.toList());

                    if(writerSearchPolicy.size() == 1){
                        Map<String, String> param = writerSearchPolicy.get(0).getParam()
                                .orElseThrow(() -> new IllegalStateException("ERROR: cannot get parameters."));
                        String writer = param.get("writer");

                        return notice.getWriter().contains(writer);
                    }
                    else
                        return false;
                })
                // 날짜 순으로 정렬
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

    @Override
    public boolean editNotice(String regNo, Notice notice) {

        List<Integer> idx = IntStream.range(0, repository.size())
                .filter(i -> repository.get(i).getRegistNo().equals(regNo))
                .boxed()
                .collect(Collectors.toList());

        if(idx.size() == 1){
            repository.set(idx.get(0), notice);
            return true;
        }
        else{
            return false;
        }
    }
}
