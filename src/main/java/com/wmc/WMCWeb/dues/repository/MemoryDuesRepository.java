package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryDuesRepository {
    private static Map<String, Dues> store = new HashMap();
    private static long sequence = 0L;


   // @Override
   /* public Dues save(Dues dues){
        dues.setRegId(String.valueOf(++sequence));
        store.put(dues.getRegId(), dues);
        return dues;
    }*/

    public Optional<Dues> findById(String regId){
        return Optional.ofNullable(store.get(regId));
    }

    //@Override
    public List<Dues> findDue(Map<String, String> param){
        return new ArrayList<>(store.values());
    }

    public Optional<Dues> findByState(String state){
        return store.values().stream()
                .filter(dues->dues.getState().equals(state))
                .findAny();
    }




    public void clearStore(){
        store.clear();
    }


}
