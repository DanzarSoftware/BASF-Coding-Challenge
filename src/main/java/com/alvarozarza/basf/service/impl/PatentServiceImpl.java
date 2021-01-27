package com.alvarozarza.basf.service.impl;

import com.alvarozarza.basf.exception.MongoDbException;
import com.alvarozarza.basf.model.Patent;
import com.alvarozarza.basf.repository.PatentRepository;
import com.alvarozarza.basf.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatentServiceImpl implements PatentService {

    @Autowired
    private PatentRepository patentRepository;


    @Override
    public List<Patent> findAll() {
        try {
            return patentRepository.findAll();
        } catch (Exception e) {
            throw new MongoDbException(e.getMessage());
        }
    }

    @Override
    public List<Patent> findPatentsByChemical(String chemical) {
        try {
            return patentRepository.findByEntitiesIn(chemical);
        } catch (Exception e) {
            throw new MongoDbException(e.getMessage());
        }
    }

}
