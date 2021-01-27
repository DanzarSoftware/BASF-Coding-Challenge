package com.alvarozarza.basf.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@ApiModel
public class Patent {

    @ApiModelProperty
    private Integer year;

    @ApiModelProperty
    private String title;

    @ApiModelProperty
    private String abstractContent;

    @ApiModelProperty
    private String fullText;

    @ApiModelProperty
    private String applicants;

    @ApiModelProperty
    private Set<String> entities;

    public Patent() {
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractContent() {
        return abstractContent;
    }

    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }


    public String getApplicants() {

        return applicants;
    }

    public void setApplicants(String applicants) {
        this.applicants = applicants;
    }

    public Set<String> getEntities() {
        return entities;
    }

    public void setEntities(Set<String> entities) {
        this.entities = entities;
    }
}
