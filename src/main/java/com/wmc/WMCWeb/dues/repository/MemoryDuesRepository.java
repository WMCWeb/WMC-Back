package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryDuesRepository implements DuesRepository{
    private static Map<Long, Dues> store = new HashMap();
    private static long sequence = 0L;


    @Override
    public Dues save(Dues dues){
        dues.setRegId(++sequence);
        store.put(dues.getRegId(), dues);
        return dues;
    }

    @Override
    public Optional<Dues> findById(Long regId){
        return Optional.ofNullable(store.get(regId));
    }

    @Override
    public List<Dues> findAll(){
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Dues> findByState(String state){
        return store.values().stream()
                .filter(dues->dues.getState().equals(state))
                .findAny();
    }




    public void clearStore(){
        store.clear();
    }


}
