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
                    if (mutantSequences > 1) return true; // Si encuentra más de 1 secuencia, es mutante
                }
            }
        }

        return mutantSequences > 1;
    }

    // Método para verificar secuencias consecutivas desde una posición dada
    private boolean checkSequence(char[][] matrix, int row, int col, int rowIncrement, int colIncrement) {
        int count = 1;
        char startChar = matrix[row][col];

        for (int k = 1; k < 4; k++) { // Buscamos una secuencia de 4 caracteres
            int newRow = row + k * rowIncrement;
            int newCol = col + k * colIncrement;

            if (newRow >= matrix.length || newCol >= matrix[0].length || newCol < 0 || matrix[newRow][newCol] != startChar) {
                return false; // Si se sale de los límites o no coincide, no hay secuencia
            }

            count++;
        }

        return count == 4; // Si encontró una secuencia de 4 caracteres consecutivos
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
