package com.alvarozarza.basf.repository;

import com.alvarozarza.basf.model.Patent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatentRepository extends MongoRepository<Patent, Integer> {
    List<Patent> findByEntitiesIn(String entity);
}
