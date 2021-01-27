package com.alvarozarza.basf.service.impl;


import com.alvarozarza.basf.constants.Constants;
import com.alvarozarza.basf.service.NerChemicalTaggerService;
import com.alvarozarza.basf.utils.XmlUtils;
import org.springframework.stereotype.Service;
import uk.ac.cam.ch.wwmm.chemicaltagger.ChemistryPOSTagger;
import uk.ac.cam.ch.wwmm.chemicaltagger.ChemistrySentenceParser;
import uk.ac.cam.ch.wwmm.chemicaltagger.POSContainer;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class NerChemicalTaggerServiceImpl implements NerChemicalTaggerService {


    @Override
    public Set<String> extractTagsFromText(String text) {
        POSContainer posContainer = ChemistryPOSTagger.getDefaultInstance().runTaggers(text);

        // Returns a string of TAG TOKEN format (OSCAR-CM)
        // Call ChemistrySentenceParser either by passing the POSContainer or by InputStream
        ChemistrySentenceParser chemistrySentenceParser = new ChemistrySentenceParser(posContainer);

        // Create a parseTree of the tagged input and generate xml document
        chemistrySentenceParser.parseTags();
        nu.xom.Document xomDocument = chemistrySentenceParser.makeXMLDocument();

        //Search the OSCAR tags
        Set<String> entities = XmlUtils.getStringFromXmlNodeStringXml(xomDocument.toXML(), "OSCAR-CM");
        return entities.stream().filter(((Predicate<String>) Constants.notValidValues::contains).negate()).collect(Collectors.toSet());
    }
}
