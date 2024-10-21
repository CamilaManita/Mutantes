package com.example.PrimerParcial.repositories;

import com.example.PrimerParcial.entities.DNA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DNARepository extends JpaRepository<DNA, Long> {
    Optional<DNA> findByDna(String dna);

    long countByIsMutant(boolean isMutant);

}
