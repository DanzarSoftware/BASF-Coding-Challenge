package com.alvarozarza.basf.service;

import com.alvarozarza.basf.model.Patent;

import java.util.List;

public interface PatentService {
    List<Patent> findAll();

    List<Patent> findPatentsByChemical(String chemical);
}
