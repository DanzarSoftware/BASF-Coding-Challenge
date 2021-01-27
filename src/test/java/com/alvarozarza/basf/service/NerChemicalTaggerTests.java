package com.alvarozarza.basf.service;


import com.alvarozarza.basf.service.impl.NerChemicalTaggerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class NerChemicalTaggerTests {


    @InjectMocks
    private NerChemicalTaggerServiceImpl nerChemicalTaggerService;


    @Test
    public void extractTagsFromText() {

        String text = "A solution of 124C (7.0 g, 32.4 mmol) in concentrate H2SO4 " +
                "(9.5 mL) was added to a solution of concentrate H2SO4 (9.5 mL) " +
                "and fuming HNO3 (13 mL) and the mixture was heated at 60°C for " +
                "30 min. After cooling to room temperature, the reaction mixture " +
                "was added to iced 6M solution of NaOH (150 mL) and neutralized " +
                "to pH 6 with 1N NaOH solution. The reaction mixture was extracted " +
                "with dichloromethane (4x100 mL). The combined organic phases were " +
                "dried over Na2SO4, filtered and concentrated to give 124D as a solid.";

        Set<String> taggedValues = new HashSet<>(Arrays.asList("H2SO4","Na2SO4","dichloromethane","NaOH","HNO3"));
        Set<String> returnTaggedValues = nerChemicalTaggerService.extractTagsFromText(text);


        assertThat(taggedValues.size()).isEqualTo(returnTaggedValues.size());
        assertThat(returnTaggedValues.containsAll(taggedValues.stream().collect(Collectors.toList()))).isEqualTo(true);

    }




}
