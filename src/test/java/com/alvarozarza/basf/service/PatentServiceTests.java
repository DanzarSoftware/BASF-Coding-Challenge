package com.alvarozarza.basf.service;


import com.alvarozarza.basf.exception.MongoDbException;
import com.alvarozarza.basf.model.Patent;
import com.alvarozarza.basf.repository.PatentRepository;
import com.alvarozarza.basf.service.impl.PatentServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PatentServiceTests {


    @InjectMocks
    private PatentServiceImpl patentService;

    @Mock
    private PatentRepository patentRepository;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void findAllPatentsOK() {

        List<Patent> patentList = new ArrayList<>(Arrays.asList(new Patent(), new Patent(), new Patent()));
        // When
        when(patentRepository.findAll()).thenReturn(patentList);

        List<Patent> returnList = patentService.findAll();
        assertThat(patentList).isEqualTo(returnList);
    }

    @Test
    public void findAllPatentsKO() {
        // Expected exception
        expectedEx.expect(MongoDbException.class);

        // When
        when(patentRepository.findAll()).thenThrow(new MongoDbException());

        patentService.findAll();

    }

    @Test
    public void findPatentsByChemicalOK() {
        List<Patent> patentList = new ArrayList<>(Arrays.asList(new Patent(), new Patent(), new Patent()));

        // When
        when(patentRepository.findByEntitiesIn(any())).thenReturn(patentList);

        List<Patent> returnList = patentService.findPatentsByChemical("example");
        assertThat(patentList).isEqualTo(returnList);

    }

    @Test
    public void findPatentsByChemicalKO() {
        // Expected exception
        expectedEx.expect(MongoDbException.class);

        // When
        when(patentRepository.findByEntitiesIn((any()))).thenThrow(new MongoDbException());

        patentService.findPatentsByChemical("example");

    }
}
