package com.example.PrimerParcial.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Dna")
public class DNA{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dna")
    private String dna;

    @Column(name = "isMutant")
    private boolean isMutant;

    public DNA(String s) {
    }
}
