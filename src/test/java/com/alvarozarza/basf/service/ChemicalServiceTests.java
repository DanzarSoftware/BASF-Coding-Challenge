package com.alvarozarza.basf.service;


import com.alvarozarza.basf.exception.MongoDbException;
import com.alvarozarza.basf.model.Patent;
import com.alvarozarza.basf.repository.PatentRepository;
import com.alvarozarza.basf.service.impl.ChemicalServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ChemicalServiceTests {


    @InjectMocks
    private ChemicalServiceImpl chemicalService;

    @Mock
    private NerChemicalTaggerService nerChemicalTaggerService;

    @Mock
    private PatentRepository patentRepository;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private Path testFile = Paths.get("Test150.zip");


    @Test
    public void executeExtractionPipelineFoundEntitiesOK() throws IOException {

        // Mock objects
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", Files.readAllBytes(testFile));
        Set<String> entities = new HashSet<>(Arrays.asList("water", "NaCl", "n-propylnorapomorphine"));
        List<Patent> patentList = new ArrayList<>(Arrays.asList(new Patent(), new Patent(), new Patent()));

        // When
        when(nerChemicalTaggerService.extractTagsFromText(any())).thenReturn(entities);
        when(nerChemicalTaggerService.extractTagsFromText(any())).thenReturn(entities);
        when(patentRepository.saveAll(any())).thenReturn(patentList);

        Boolean result = chemicalService.executeExtractionPipeline(mockMultipartFile);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void executeExtractionPipelineNotFoundEntitiesOK() throws IOException {

        // Mock objects
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", Files.readAllBytes(testFile));
        Set<String> entities = new HashSet<>();
        List<Patent> patentList = new ArrayList<>(Arrays.asList(new Patent(), new Patent(), new Patent()));

        // When
        when(nerChemicalTaggerService.extractTagsFromText(any())).thenReturn(entities);
        when(nerChemicalTaggerService.extractTagsFromText(any())).thenReturn(entities);
        when(patentRepository.saveAll(any())).thenReturn(patentList);

        Boolean result = chemicalService.executeExtractionPipeline(mockMultipartFile);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void executeExtractionPipelineNotFoundEntitiesKO() throws IOException {

        // Expected exception
        expectedEx.expect(MongoDbException.class);

        // Mock objects
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", Files.readAllBytes(testFile));
        Set<String> entities = new HashSet<>();

        // When
        when(nerChemicalTaggerService.extractTagsFromText(any())).thenReturn(entities);
        when(nerChemicalTaggerService.extractTagsFromText(any())).thenReturn(entities);
        when(patentRepository.saveAll(any())).thenThrow(new MongoDbException());

        Boolean result = chemicalService.executeExtractionPipeline(mockMultipartFile);

        assertThat(result).isNotEqualTo(true);
    }

    @Test
    public void removeDatabaseOK() {

        patentRepository.deleteAll();
    }


}
