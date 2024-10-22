package com.example.PrimerParcial.services;

import com.example.PrimerParcial.entities.DNA;
import com.example.PrimerParcial.repositories.DNARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DNAServiceImplTest {

    @Mock
    private DNARepository dnaRepository;

    @InjectMocks
    private DNAServiceImpl dnaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsMutant_ReturnsTrue_WhenMutantSequenceExists() throws Exception {
        DNA dna = new DNA();
        dna.setDna("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCAGC,AATTTG");

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());
        when(dnaRepository.save(any(DNA.class))).thenReturn(dna);

        boolean isMutant = dnaService.isMutant(dna);

        assertTrue(isMutant);
        verify(dnaRepository, times(1)).save(any(DNA.class));
    }

    @Test
    public void testIsMutant_ReturnsFalse_WhenMutantSequenceDoesNotExist() throws Exception {
        DNA dna = new DNA();
        // Esta secuencia no tiene ninguna secuencia de 4 iguales consecutivas
        dna.setDna("ATGCGA,CAGTAC,TTATGT,AGAGTG,CGCAGC,TCTTTG");

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());
        when(dnaRepository.save(any(DNA.class))).thenReturn(dna);

        boolean isMutant = dnaService.isMutant(dna);

        assertFalse(isMutant); // Se espera que no detecte un mutante
        verify(dnaRepository, times(1)).save(any(DNA.class)); // Debería intentar guardar el ADN
    }




    @Test
    public void testIsMutant_ReturnsTrue_WhenDnaExistsInRepository() throws Exception {
        DNA dna = new DNA();
        dna.setDna("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCAGC,AATTTG");

        DNA existingDna = new DNA();
        existingDna.setDna("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCAGC,AATTTG");
        existingDna.setMutant(true);

        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.of(existingDna));

        boolean isMutant = dnaService.isMutant(dna);

        assertTrue(isMutant);
        verify(dnaRepository, never()).save(any(DNA.class));
    }

}
