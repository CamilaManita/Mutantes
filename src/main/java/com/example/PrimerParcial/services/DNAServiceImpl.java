package com.example.PrimerParcial.services;

import com.example.PrimerParcial.entities.DNA;
import com.example.PrimerParcial.repositories.DNARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DNAServiceImpl implements com.example.PrimerParcial.services.DNAService {
    @Autowired
    private DNARepository dnaRepository;

    @Override
    public boolean isMutant(DNA dna) {
        // Convertimos la secuencia de ADN a String[]
        String dnaSequence = dna.getDna();
        if (dnaSequence == null || dnaSequence.isEmpty()) {
            throw new IllegalArgumentException("DNA sequence cannot be null or empty");
        }
        String[] dnaArray = dnaSequence.split(",");

        // Verifica si ya existe en la base de datos
        Optional<DNA> existingDna = dnaRepository.findByDna(dnaSequence);
        if (existingDna.isPresent()) {
            return existingDna.get().isMutant();
        }

        // Inicializa la lógica para detección de mutante en una sola pasada
        boolean mutantDetected = detectMutantInOnePass(dnaArray);

        // Guarda en la base de datos
        dnaRepository.save(DNA.builder().dna(dnaSequence).isMutant(mutantDetected).build());

        return mutantDetected;
    }



    private boolean detectMutantInOnePass(String[] dnaArray) {
        int n = dnaArray.length;
        char[][] matrix = convertToMatrix(dnaArray);
        int mutantSequences = 0;

        // Recorremos la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Verifica horizontal, vertical y diagonal desde la posición actual
                if (checkSequence(matrix, i, j, 0, 1) || // Horizontal
                    checkSequence(matrix, i, j, 1, 0) || // Vertical
                    checkSequence(matrix, i, j, 1, 1) || // Diagonal hacia abajo derecha
                    checkSequence(matrix, i, j, 1, -1)) // Diagonal hacia abajo izquierda
                {
                    mutantSequences++;
                    if (mutantSequences >= 2) { // Si hay 2 o más secuencias, es mutante
                        return true;
                    }
                }
            }
        }

        return false; // Solo es mutante si hay 2 o más secuencias
    }

    private boolean checkSequence(char[][] matrix, int row, int col, int rowIncrement, int colIncrement) {
        char startChar = matrix[row][col];

        // Verificar si es posible tener una secuencia de 4 caracteres desde la posición actual
        for (int k = 1; k < 4; k++) {
            int newRow = row + k * rowIncrement;
            int newCol = col + k * colIncrement;

            // Si se sale de los límites de la matriz, no hay secuencia
            if (newRow >= matrix.length || newRow < 0 || newCol >= matrix[0].length || newCol < 0 || matrix[newRow][newCol] != startChar) {
                return false;
            }
        }

        return true; // Si se cumple la secuencia de 4 caracteres
    }


    private char[][] convertToMatrix(String[] dna) {
        int n = dna.length;
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        return matrix;
    }
}
