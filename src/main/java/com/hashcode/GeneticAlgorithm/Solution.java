package com.hashcode.GeneticAlgorithm;

import java.util.*;

import com.hashcode.Solver;
import com.hashcode.Simulation.Project;
import com.hashcode.Simulation.Requirement;

/**
 * A solution contains an order in which the projects have to be executed and
 * the assigned contributors to each of the project requirements
 * ArrayList<ContributorIDS of the ith project jth requirement>
 */
public class Solution {
    public int[] order;
    public ArrayList<Project> projectsWithAssignedContributors;
    public double fitness = 0;

    public boolean isFree[][];

    public int totalDurationOfSimulation = Solver.totalDurationOfSimulation;

    public Solution(int[] order, ArrayList<Project> projectsWithAssignedContributors) {
        this.order = order;
        this.projectsWithAssignedContributors = projectsWithAssignedContributors;
        this.isFree = new boolean[this.totalDurationOfSimulation + 1][Solver.numberOfContributors + 1];
        for (int i = 0; i < this.totalDurationOfSimulation; i++) {
            for (int j = 0; j < Solver.numberOfContributors; j++) {
                this.isFree[i][j] = true;
            }
        }
    }

    public double getFitness() {
        for (int i = 0; i < this.order.length; i++) {
            Project p = this.projectsWithAssignedContributors.get(order[i]);
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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Project project : this.projectsWithAssignedContributors) {
            sb.append(project.name);
            sb.append("\n");
            for (Requirement req : project.requirements) {
                sb.append(req.skill + " => " + Solver.contributors.get(req.assignedContributorID).name + " | ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
