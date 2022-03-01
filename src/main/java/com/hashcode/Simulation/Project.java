package com.hashcode.Simulation;

import java.util.*;

public class Project {
    public String name;
    public ArrayList<Requirement> requirements;
    public ArrayList<Integer> contributorIDS;
    public int duration;
    public int bestBefore;
    public int score;

    public Project(String name, ArrayList<Requirement> requirements, ArrayList<Integer> contributorIDS, int duration,
            int bestBefore,
            int score) {
        this.name = name;
        this.requirements = requirements;
        this.contributorIDS = contributorIDS;
        this.duration = duration;
        this.bestBefore = bestBefore;
        this.score = score;
    }

    public Project(String name, ArrayList<Requirement> requirements, int duration, int bestBefore, int score) {
        this.name = name;
        this.requirements = requirements;
        this.duration = duration;
        this.bestBefore = bestBefore;
        this.score = score;
    }
}
