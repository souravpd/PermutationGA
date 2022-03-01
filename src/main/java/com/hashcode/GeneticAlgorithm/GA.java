package com.hashcode.GeneticAlgorithm;

import java.util.*;

import com.hashcode.Solver;
import com.hashcode.Simulation.Contributor;
import com.hashcode.Simulation.Project;
import com.hashcode.Simulation.Requirement;
import com.hashcode.Simulation.Simulation;

/**
 * The assingment of contributors depends upon the order in which the projects
 * are executed
 * 
 * 
 */

public class GA {
    public int generationSize;

    public GA(int generationSize) {
        this.generationSize = generationSize;
    }

    @SuppressWarnings("unchecked")
    public List<Solution> initialPopulation() {
        List<Solution> population = new ArrayList<>();
        int[] order = Solver.arrayOfProjects;
        while (nextPermutation.findNextPermutation(order)) {
            /**
             * We are now starting the simulation
             * Create an ordered list of projects with the assigned contributors
             * 
             */

            boolean isOrderValid = true;
            ArrayList<Project> projectsWithAssignedContributors = new ArrayList<Project>();
            for (int i = 0; i < order.length; i++) {
                Project project = Solver.inputProjects.get(order[i]);
                ArrayList<Requirement> satisifiedRequirements = new ArrayList<Requirement>();
                if (Simulation.assignContributorToRequirementList(project.requirements, 0, new ArrayList<Contributor>(),
                        satisifiedRequirements, (HashMap<Integer, Contributor>) Solver.contributors.clone())) {
                    ArrayList<Integer> assignedContributorIDS = new ArrayList<Integer>();
                    for (Requirement req : satisifiedRequirements) {
                        assignedContributorIDS.add(req.assignedContributorID);
                    }
                    Project solutionProject = new Project(project.name, satisifiedRequirements, assignedContributorIDS,
                            project.duration, project.bestBefore, project.score);
                    projectsWithAssignedContributors.add(solutionProject);
                } else {
                    isOrderValid = false;
                    break;
                }
            }

            if (isOrderValid) {
                System.out.println("Valid order " + Arrays.toString(order));
                Solution solution = new Solution(order, projectsWithAssignedContributors);
                System.out.println(solution);
                population.add(solution);
                System.out.println();
            } else {
                System.out.println("Invalid Order => " + Arrays.toString(order));
            }
        }
        return population;
    }
}
