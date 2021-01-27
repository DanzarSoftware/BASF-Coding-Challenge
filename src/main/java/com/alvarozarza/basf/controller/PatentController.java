package com.alvarozarza.basf.controller;

import com.alvarozarza.basf.model.Patent;
import com.alvarozarza.basf.service.PatentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/patent")
public class PatentController {

    @Autowired
    private PatentService patentService;



    @GetMapping
    @ApiOperation(value = "Get all the patents")
    public List<Patent> getPatents(List<Patent> patentList){
        return patentService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "/{chemical}")
    @ApiOperation(value = "Get patents that contain a chemical")
    public List<Patent> getPatentsByChemical(@ApiParam(value = "The chemical name", name = "chemical", required = true) @PathVariable(value = "chemical") String chemical){
        return patentService.findPatentsByChemical(chemical);

    }


}
