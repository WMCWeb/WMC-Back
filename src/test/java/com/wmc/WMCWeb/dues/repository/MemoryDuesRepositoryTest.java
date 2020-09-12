package com.wmc.WMCWeb.dues.repository;

import com.wmc.WMCWeb.dues.domain.Dues;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MemoryDuesRepositoryTest {

    MemoryDuesRepository repository = new MemoryDuesRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Dues dues = new Dues();
        dues.setState(1);
        dues.setAmount(2000);

        repository.save(dues);

        Dues result = repository.findById(dues.getId()).get();
       // assertThat(dues).isEqualTo(result);
    }

    @Test
    public void findByState(){
        Dues dues1 = new Dues();
        dues1.setState(1);
        dues1.setAmount(2000);
        repository.save(dues1);

        Dues dues2 = new Dues();
        dues2.setState(0);
        dues2.setAmount(3000);
        repository.save(dues2);


        Dues result = repository.findByState(0).get();
     //   assertThat(result).isEqualTo(dues2);
    }

    @Test
    public void findAll() {
        //given
        Dues dues1 = new Dues();
        dues1.setState(1);
        dues1.setAmount(20000);
        repository.save(dues1);

        Dues dues2 = new Dues();
        dues2.setState(0);
        dues2.setAmount(30000);
        repository.save(dues2);

        //when
        List<Dues> result = repository.findAll();
        //then
  //      assertThat(result.size()).isEqualTo(2);
    }

}
