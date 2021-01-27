package com.alvarozarza.basf.service;

import java.util.Set;

public interface NerChemicalTaggerService {
    Set<String> extractTagsFromText(String text);
}
