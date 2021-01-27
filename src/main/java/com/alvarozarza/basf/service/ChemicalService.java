package com.alvarozarza.basf.service;

import org.springframework.web.multipart.MultipartFile;

public interface ChemicalService {
    Boolean executeExtractionPipeline(MultipartFile requestsFile);
    void removeData();
}
