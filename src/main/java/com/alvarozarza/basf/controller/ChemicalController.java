package com.alvarozarza.basf.controller;

import com.alvarozarza.basf.service.ChemicalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/chemical")
public class ChemicalController {

    @Autowired
    private ChemicalService chemicalService;


    @PostMapping
    @ApiOperation(value = "Extract chemicals from patents")
    public ResponseEntity<String> extractChemicals(
            @ApiParam(value = "Zip archive of patents(with XML format)", name = "patentsArchive", required = true) @RequestParam("patentsArchive") MultipartFile patentsArchive) {
        if(chemicalService.executeExtractionPipeline(patentsArchive)){
            return ResponseEntity.ok("The extraction pipeline ends sucessfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error with the extraction pipeline");

    }

    @DeleteMapping
    @ApiOperation(value = "Delete the whole database ")
    public void removeDatabase(){
        chemicalService.removeData();

    }

}
