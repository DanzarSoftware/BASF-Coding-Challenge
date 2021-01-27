package com.alvarozarza.basf.controller;


import com.alvarozarza.basf.service.ChemicalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Ignore;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChemicalControllerTests {


    @InjectMocks
    private ChemicalController chemicalController;

    @Mock
    private ChemicalService chemicalService;

    private Path testFile = Paths.get("Test150.zip");

    @Test
    @Ignore
    public void extractChemicals() throws IOException {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", Files.readAllBytes(testFile));

        // When
        when(chemicalService.executeExtractionPipeline(any())).thenReturn(true);
        ResponseEntity<String> response = chemicalController.extractChemicals(mockMultipartFile);

        // Then
        assertThat(response.getStatusCode().is2xxSuccessful()).isEqualTo(true);
    }

    @Test
    public void removeDatabase() {

        chemicalController.removeDatabase();
    }

}
