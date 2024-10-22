package com.example.PrimerParcial.controllers;

import com.example.PrimerParcial.dto.DNARequest;
import com.example.PrimerParcial.services.DNAService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DNAController.class)
class DNAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DNAService dnaService;

    @Test
    void detectMutant_ReturnsOk_WhenMutantDetected() throws Exception {
        Mockito.when(dnaService.isMutant(Mockito.any())).thenReturn(true);

        mockMvc.perform(post("/api/dna/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}"))
                .andExpect(status().isOk())  // Esperamos status 200 OK
                .andExpect(content().json("{\"mutant\":\"Mutante encontrado.\"}"));
    }

    @Test
    void detectMutant_ReturnsForbidden_WhenNotMutant() throws Exception {
        // Simulamos que el servicio devuelve false, indicando que NO es mutante
        Mockito.when(dnaService.isMutant(Mockito.any())).thenReturn(false);

        // Realizamos una petición POST con una secuencia ADN de ejemplo
        mockMvc.perform(post("/api/dna/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCGTA\",\"TCACTG\"]}"))
                .andExpect(status().isForbidden())  // Esperamos status 403 Forbidden
                .andExpect(content().json("{\"mutant\":\"Mutante no encontrado.\"}"));
    }

}
