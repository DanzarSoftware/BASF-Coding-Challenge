package com.alvarozarza.basf.controller;


import com.alvarozarza.basf.model.Patent;
import com.alvarozarza.basf.service.PatentService;
import org.junit.Test;
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
public class PatentControllerTests {


    @InjectMocks
    private PatentController patentController;

    @Mock
    private PatentService patentService;


    @Test
    public void getPatents() {

        List<Patent> patentList = new ArrayList<>(Arrays.asList(new Patent(), new Patent(), new Patent()));

        // When
        when(patentService.findAll()).thenReturn(patentList);

        List<Patent> response = patentController.getPatents(patentList);

        // Then
        assertThat(response).isEqualTo(patentList);
    }

    @Test
    public void getPatentsByChemical() {

        List<Patent> patentList = new ArrayList<>(Arrays.asList(new Patent(), new Patent(), new Patent()));

        // When
        when(patentService.findPatentsByChemical(any())).thenReturn(patentList);

        List<Patent> response = patentController.getPatentsByChemical("test");

        // Then
        assertThat(response).isEqualTo(patentList);
    }

}
