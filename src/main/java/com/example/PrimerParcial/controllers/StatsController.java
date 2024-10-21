package com.example.PrimerParcial.controllers;

import com.example.PrimerParcial.services.DNAStatsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/dna/stats")
public class StatsController{

    @Autowired
    DNAStatsServiceImpl dnaStatsService;

    @GetMapping("/calcular/")
    public ResponseEntity<?> calcularStats(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(dnaStatsService.calcularStats());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
