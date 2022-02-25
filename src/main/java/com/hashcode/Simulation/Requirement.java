package com.hashcode.Simulation;

public class Requirement {
    public String skill;
    public int level;
    public int assignedContributorID;

    Requirement(String skill, int level, int assignedContributorID) {
        this.skill = skill;
        this.level = level;
        this.assignedContributorID = assignedContributorID;
    }
}
