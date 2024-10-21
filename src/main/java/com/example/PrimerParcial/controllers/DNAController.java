package com.example.PrimerParcial.controllers;

import com.example.PrimerParcial.dto.DNARequest;
import com.example.PrimerParcial.entities.DNA;
import com.example.PrimerParcial.services.DNAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/dna")
public class DNAController{
    @Autowired
    DNAService dnaService;

    @PostMapping("/mutant/")
    public ResponseEntity<?> detect(@RequestBody DNARequest dnaRequest){
        try{
            String dnaString = String.join(",", dnaRequest.getDna());

            DNA dnaEntity = new DNA();
            dnaEntity.setDna(dnaString);

            boolean isMutant = dnaService.isMutant(dnaEntity);

            if (isMutant) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"mutant\":\"Mutante encontrado.\"}");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"mutant\":\"Mutante no encontrado.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
