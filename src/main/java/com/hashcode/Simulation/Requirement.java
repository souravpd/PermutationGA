package com.hashcode.Simulation;

public class Requirement {
    public String skill;
    public int level;
    public int assignedContributorID;

    public Requirement(String skill, int level, int assignedContributorID) {
        this.skill = skill;
        this.level = level;
        this.assignedContributorID = assignedContributorID;
    }

    public Requirement(String skill, int level) {
        this.skill = skill;
        this.level = level;
        this.assignedContributorID = -1;
    }
}
