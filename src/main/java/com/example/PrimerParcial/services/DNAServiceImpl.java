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
        String dnaSequence = dna.getDna();
        if (dnaSequence == null || dnaSequence.isEmpty()) {
            throw new IllegalArgumentException("DNA sequence cannot be null or empty");
        }
        String[] dnaArray = dnaSequence.split(",");

        Optional<DNA> existingDna = dnaRepository.findByDna(dnaSequence);
        if (existingDna.isPresent()) {
            return existingDna.get().isMutant();
        }

        boolean mutantDetected = detectMutantInOnePass(dnaArray);

        dnaRepository.save(DNA.builder().dna(dnaSequence).isMutant(mutantDetected).build());

        return mutantDetected;
    }



    private boolean detectMutantInOnePass(String[] dnaArray) {
        int n = dnaArray.length;
        char[][] matrix = convertToMatrix(dnaArray);
        int mutantSequences = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (checkSequence(matrix, i, j, 0, 1) ||
                    checkSequence(matrix, i, j, 1, 0) ||
                    checkSequence(matrix, i, j, 1, 1) ||
                    checkSequence(matrix, i, j, 1, -1))
                {
                    mutantSequences++;
                    if (mutantSequences >= 2) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkSequence(char[][] matrix, int row, int col, int rowIncrement, int colIncrement) {
        char startChar = matrix[row][col];

        for (int k = 1; k < 4; k++) {
            int newRow = row + k * rowIncrement;
            int newCol = col + k * colIncrement;

            if (newRow >= matrix.length || newRow < 0 || newCol >= matrix[0].length || newCol < 0 || matrix[newRow][newCol] != startChar) {
                return false;
            }
        }

        return true;
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
