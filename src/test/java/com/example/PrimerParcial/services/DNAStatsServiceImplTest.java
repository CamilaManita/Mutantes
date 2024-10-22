package com.example.PrimerParcial.services;

import com.example.PrimerParcial.entities.DNAStats;
import com.example.PrimerParcial.repositories.DNARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DNAStatsServiceImplTest {

    @Mock
    private DNARepository dnaRepository;

    @InjectMocks
    private DNAStatsServiceImpl dnaStatsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalcularStats_WithMutantAndNonMutantDNA() throws Exception {
        when(dnaRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRepository.countByIsMutant(false)).thenReturn(5L);

        DNAStats stats = dnaStatsService.calcularStats();

        assertEquals(10L, stats.getMutantCount());
        assertEquals(5L, stats.getNonMutantCount());
        assertEquals(2.0, stats.getMutantRatio(), 0.001);
    }

    @Test
    public void testCalcularStats_WithOnlyMutantDNA() throws Exception {
        when(dnaRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRepository.countByIsMutant(false)).thenReturn(0L);

        DNAStats stats = dnaStatsService.calcularStats();

        assertEquals(10L, stats.getMutantCount());
        assertEquals(0L, stats.getNonMutantCount());
        assertEquals(0.0, stats.getMutantRatio(), 0.001);
    }

    @Test
    public void testCalcularStats_WithOnlyNonMutantDNA() throws Exception {
        when(dnaRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRepository.countByIsMutant(false)).thenReturn(5L);

        DNAStats stats = dnaStatsService.calcularStats();

        assertEquals(0L, stats.getMutantCount());
        assertEquals(5L, stats.getNonMutantCount());
        assertEquals(0.0, stats.getMutantRatio(), 0.001);
    }
}
