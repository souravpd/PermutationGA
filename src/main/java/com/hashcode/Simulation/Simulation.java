package com.hashcode.Simulation;

import java.util.*;

import com.hashcode.Solver;

/**
 * Simulation
 * Project
 * - D S B R
 * Total time required = 5+7+10
 * Name Anna Bob Maria
 * 0 Webserver Webserver
 * 1 Webserver Webserver
 * 2 Webserver Webserver
 * 3 Webserver Webserver
 * 4 Webserver Webserver
 * 5 Webserver Webserver
 * 6 Webserver Webserver
 * 7 Logging
 * 8 Logging
 * 9 Logging
 * 10 Logging
 * 11 Logging
 * 12 Logging
 * 13 Logging
 * 14
 * 15
 * 16
 * 17
 * 18
 * 19
 * 20
 * 21
 * 22
 */
/**
 * Input
 * order in which the projects have to executed
 * ArrayList<Project> projects
 * ArrayList<Contributor> contributors
 * int totalDurationOfSimulation
 * Project => (D,S,B,R), ArrayList<ContributorID>
 * 
 */
public class Simulation {
    // Contains the final fitness of running the simulation
    public double fitness = 0;
    // Contains the Order in which the projects have to be executed
    public int order[];
    // Checks whether a contributor is free at this instant
    public boolean isFree[][];
    // Total duration of the simulation (sum of all the project durations)
    public int totalDurationOfSimulation;
    // Quickly get contributor by contributorID
    public HashMap<Integer, Contributor> contributors;

    // List of projects
    public ArrayList<Project> projects;

    public Simulation(int totalDurationOfSimulation, int order[], ArrayList<Project> projects,
            HashMap<Integer, Contributor> contributors) {
        this.totalDurationOfSimulation = totalDurationOfSimulation;
        this.projects = projects;
        this.order = order;
        this.contributors = contributors;
        // All the people are free initially
        this.isFree = new boolean[this.totalDurationOfSimulation + 1][Solver.numberOfContributors + 1];
        for (int i = 0; i < this.totalDurationOfSimulation; i++) {
            for (int j = 0; j < Solver.numberOfContributors; j++) {
                this.isFree[i][j] = true;
            }
        }
    }

    /**
     * Input => Project with Requirements(req.skill and req.level,
     * req.assignedContributorID)
     * HashMap<ContributorID, Contributor> getContributorByID();
     * Contributor
     * - Name
     * - HashMap<Skill, Level>
     * 
     */

    public boolean isValid(Project project) {
        for (Requirement req : project.requirements) {
            String skill = req.skill;
            int level = req.level;
            Contributor assignedContributor = this.contributors.get(req.assignedContributorID);
            if (assignedContributor.skills.get(skill) >= req.level) {
                continue;
            } else if (assignedContributor.skills.get(skill) == req.level - 1) {
                // Find if there exists someone who can mentor him
                boolean foundMentor = false;
                for (int i = 0; i < project.contributorIDS.size(); i++) {
                    if (this.contributors.get(project.contributorIDS.get(i)).skills.get(skill) >= level) {
                        foundMentor = true;
                        continue;
                    }
                }
                if (!foundMentor) {
                    return false;
                }
            } else {
                return false;
            }
        }

        // Such an Assingment for the present project is valid
        // Increase the skill levels by one
        for (Requirement req : project.requirements) {
            String skill = req.skill;
            Contributor assignedContributor = this.contributors.get(req.assignedContributorID);
            if (assignedContributor.skills.get(skill) >= req.level) {
                continue;
            } else if (assignedContributor.skills.get(skill) == req.level - 1) {
                assignedContributor.skills.put(skill, assignedContributor.skills.get(skill) + 1);
            }
        }

        return true;
    }

    public double getFitness() {
        for (int i = 0; i < this.projects.size(); i++) {
            Project p = this.projects.get(i);

            if (!isValid(p)) {
                return Double.MIN_VALUE;
            }
            // Find the first spot where all contributor are free
            int timeOfAllFree = -1;
            for (int time = 0; time < this.totalDurationOfSimulation; time++) {
                boolean areAllFree = true;
                for (int id : p.contributorIDS) {
                    if (!isFree[time][id]) {
                        areAllFree = false;
                    }
                }
                if (areAllFree) {
                    timeOfAllFree = time;
                    break;
                }
            }
            int projectStartTime = timeOfAllFree;
            int projectEndTime = timeOfAllFree + p.duration;
            if (projectEndTime < p.bestBefore) {
                this.fitness = this.fitness + p.score;
            } else {
                int lateDays = projectEndTime - p.bestBefore;
                double finalScore = p.score - lateDays;
                if (finalScore < 0) {
                    finalScore = 0;
                }
                this.fitness = this.fitness + finalScore;
            }

            for (int id : p.contributorIDS) {
                for (int time = projectStartTime; time < projectEndTime; time++) {
                    isFree[time][id] = false;
                }
            }
        }
        return this.fitness;
    }

}
