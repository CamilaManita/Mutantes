# SOFTWARE DEVELOPMENT MIDTERM PROJECT
Backend midterm project for the "Software Development" course ~ 3rd year, Systems Engineering - UTN

## Introduction
Magneto wants to recruit as many mutants as possible to fight against the X-Men.

He has hired you to develop a project that can detect whether a human is a mutant based on their DNA sequence.

To achieve this, he has requested a program with a method or function using the following signature:

**isMutant(String[] dna)**

## Functionality

The method will receive an array of strings as a parameter, each representing a row in a (6x6) table with the DNA sequence. The strings can only contain the following characters: (A, T, C, G), each representing a nitrogenous base in the DNA.

A human is identified as a mutant if **MORE THAN ONE SEQUENCE** of four identical letters is found, either diagonally, horizontally, or vertically.

The rows of the matrix are input via keyboard.

Example input: '**ATCGTA**' (this corresponds to a row in the matrix)

Once the matrix is correctly entered, the function checks for the presence of mutant sequences and returns the result based on that verification.

## Execution

The project has been deployed on Render and can be accessed via the following link:

- https://mutantes-vvyg.onrender.com
- https://mutantes-vvyg.onrender.com/api/dna/stats/calcular/

### Endpoints

- **POST** /mutant - Receives a JSON with the DNA matrix to be checked. Example:

```json
{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
```

- **GET** /stats - Returns a JSON with the number of mutants and humans verified. Example:

```json
{
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
}
```

## DNA Examples

## Example of a **MUTANT** matrix:
```json
{
    "dna": [
      "ATGCGA",
      "CAGTGC",
      "TTATGT",
      "AGAAAG",
      "CCCCTA",
      "TCACTG"
    ]
}
```

## Example of a NON-MUTANT matrix:
```json
{
    "dna": [
      "ATGGTG",
      "GTCTTA",
      "AATTGG",
      "ACTAGT",
      "GGATTC", 
      "AGGCAA"
    ]
}
```

## Unit Testing
Test cases are included. The results from Jacoco, showing 80% coverage, and the stress tests performed with JMeter can be viewed in the corresponding images.

**Test**
### ![80PercentCoverage](https://github.com/user-attachments/assets/2118e8da-443a-45af-ba24-658d701d8698)

**Stress Test**
### ![image](https://github.com/user-attachments/assets/eaa45314-da05-4372-a67f-ba33105d1f8a)
### ![image](https://github.com/user-attachments/assets/1bef72a6-d168-41bb-8341-5c718acc291a)


## Sequence Diagram
It can be seen in the PDF called level3 or inside the folder called sequence diagram

