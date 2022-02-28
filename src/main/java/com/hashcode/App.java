package com.hashcode;

import java.io.*;

import com.hashcode.GeneticAlgorithm.Utils;

public class App {
    public static void main(String args[]) throws IOException {
        String filePath = "src/main/java/com/hashcode/files/A.txt";
        Solver.readInput(filePath);
        Solver.parseInput();
        Utils.run();
    }
}
