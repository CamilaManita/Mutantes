package com.example.PrimerParcial.services;

import com.example.PrimerParcial.entities.DNAStats;
import com.example.PrimerParcial.repositories.DNARepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DNAStatsServiceImpl implements com.example.PrimerParcial.services.DNAStatsService {
    @Autowired
    private DNARepository dnaRepository;

    @Override
    @Transactional
    public DNAStats calcularStats() throws Exception {
        long contadorADNMutante = dnaRepository.countByIsMutant(true);
        long contadorADNHumano = dnaRepository.countByIsMutant(false);
        double ratio;
        if (contadorADNHumano > 0) {
            ratio = (double) contadorADNMutante / contadorADNHumano;
        } else {
            ratio = 0;
        }

        // Usamos el patrón Builder para construir el objeto DNAStats sin el campo id
        return DNAStats.builder()
                .mutantCount(contadorADNMutante)
                .nonMutantCount(contadorADNHumano)
                .mutantRatio(ratio)
                .build();
    }

}
