package com.hashcode;

import java.io.*;

import com.hashcode.GeneticAlgorithm.GA;

public class App {
    public static void main(String args[]) throws IOException {
        String filePath = "src/main/java/com/hashcode/files/B.txt";
        Solver.readInput(filePath);
        Solver.parseInput();
        GA ga = new GA(100);
        ga.initialPopulation();
    }
}
/**
 * 
 */