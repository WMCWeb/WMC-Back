package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;

import java.util.List;
import java.util.Optional;

public interface DuesRepository {
    Dues save(Dues dues);
    Optional<Dues> findById(Long id);
    List<Dues> findAll();
    Optional<Dues> findByState(Integer state);
}
