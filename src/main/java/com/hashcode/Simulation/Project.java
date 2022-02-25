package com.hashcode.Simulation;

import java.util.*;

public class Project {
    public ArrayList<Requirement> requirements;
    public ArrayList<Integer> contributorIDS;
    public int duration;
    public int bestBefore;
    public int score;

    Project(ArrayList<Requirement> requirements, ArrayList<Integer> contributorIDS, int duration, int bestBefore,
            int score) {
        this.requirements = requirements;
        this.contributorIDS = contributorIDS;
        this.duration = duration;
        this.bestBefore = bestBefore;
        this.score = score;
    }

}
