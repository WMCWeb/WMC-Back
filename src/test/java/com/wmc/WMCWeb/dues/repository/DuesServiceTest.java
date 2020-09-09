package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;
import com.wmc.WMCWeb.dues.service.DuesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DuesServiceTest {

    DuesService duesService;
    MemoryDuesRepository duesRepository;

    @BeforeEach
    public void beforeEach(){
        duesRepository = new MemoryDuesRepository();
        duesService = new DuesService(duesRepository);
    }

    @AfterEach
    public void afterEach(){
        duesRepository.clearStore();
    }

    @Test
    public void 목록추가() throws Exception{

        //given
        Dues dues = new Dues();
        dues.setState(0);
        dues.setAmount(3000);

        //when
        Long saveId = duesService.register(dues);

        //then
        Dues findDues = duesRepository.findById(saveId).get();
        assertEquals(dues.getState(), findDues.getState());
        assertEquals(dues.getAmount(), findDues.getAmount());
    }
}
