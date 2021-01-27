package com.alvarozarza.basf.service.impl;


import com.alvarozarza.basf.Application;
import com.alvarozarza.basf.exception.MongoDbException;
import com.alvarozarza.basf.model.Patent;
import com.alvarozarza.basf.repository.PatentRepository;
import com.alvarozarza.basf.service.ChemicalService;
import com.alvarozarza.basf.service.NerChemicalTaggerService;
import com.alvarozarza.basf.utils.DateUtils;
import com.alvarozarza.basf.utils.XmlUtils;
import com.alvarozarza.basf.utils.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ChemicalServiceImpl implements ChemicalService {

    @Autowired
    private PatentRepository patentRepository;

    @Autowired
    private NerChemicalTaggerService nerChemicalTaggerService;

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    private Function<Document, Patent> createPatent = document -> {
        Patent patent = new Patent();
        patent.setYear(DateUtils.getYearFromDate((XmlUtils.getStringFromXmlAttribute(document, "date-produced"))));
        patent.setAbstractContent(XmlUtils.getStringFromXmlNode(document, "abstract", true));
        patent.setTitle(XmlUtils.getStringFromXmlNode(document, "invention-title", true));
        patent.setApplicants(XmlUtils.getStringFromXmlNode(document, "applicants", false));
        patent.setFullText(XmlUtils.getStringFromXmlDocument(document));
        return patent;
    };


    @Override
    public Boolean executeExtractionPipeline(MultipartFile requestsFile) {

        logger.info("Unzipping zip and converting to list of xml documents...");
        List<Document> documentList = ZipUtils.extractXmlsFromZip(requestsFile);
        logger.info("Ending document zip");

        logger.info("Creating the Patent object with the xml documents...");
        List<Patent> patents = documentList.parallelStream().map(createPatent).collect(Collectors.toList());


        logger.info("Extracting Tags from full text and abstact...");
        Consumer<Patent> extractChemicalTags = patent -> {
            Set<String> entities = nerChemicalTaggerService.extractTagsFromText(patent.getFullText());
            entities.addAll(nerChemicalTaggerService.extractTagsFromText(patent.getAbstractContent()));
            if (!CollectionUtils.isEmpty(entities)) {
                patent.setEntities(entities);
            }
        };

        patents.forEach(extractChemicalTags);

        logger.info("Saving patents into the database...");

        try {
            patentRepository.saveAll(patents);
            return true;
        } catch (Exception e) {
            throw new MongoDbException(e.getMessage());
        }

    }


    @Override
    public void removeData() {

        try {
            patentRepository.deleteAll();
        } catch (Exception e) {
            throw new MongoDbException(e.getMessage());
        }
    }
}
