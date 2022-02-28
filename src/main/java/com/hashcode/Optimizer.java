package com.hashcode;

import java.util.*;

import com.hashcode.Simulation.Project;
import com.hashcode.Simulation.Requirement;
import com.hashcode.Simulation.Simulation;
import com.hashcode.Simulation.Contributor;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

public class Optimizer implements Problem {

    @Override
    public String getName() {
        return "Optimizer";
    }

    @Override
    public int getNumberOfVariables() {
        return 1 + Solver.numberOfProjects;
    }

    @Override
    public int getNumberOfObjectives() {
        return 1;
    }

    @Override
    public int getNumberOfConstraints() {
        return 0;
    }

    @Override
    public void evaluate(Solution solution) {
        int order[] = EncodingUtils.getPermutation(solution.getVariable(0));
        ArrayList<Project> projects = new ArrayList<Project>();
        HashMap<Integer, Contributor> contributors = Solver.contributors;
        for (int i = 0; i < Solver.numberOfProjects; i++) {
            int contributorsList[] = EncodingUtils.getPermutation(solution.getVariable(i + 1));
            Project project = Solver.inputProjects.get(i);
            ArrayList<Integer> contributorIDS = new ArrayList<Integer>();
            ArrayList<Requirement> requirements = new ArrayList<Requirement>();
            int contributorsListIndex = 0;
            for (Requirement req : project.requirements) {
                int assignedContributorID = contributorsList[contributorsListIndex++];
                requirements.add(new Requirement(req.skill, req.level, assignedContributorID));
                contributorIDS.add(assignedContributorID);
            }
            projects.add(new Project(project.name, requirements, contributorIDS, project.duration, project.bestBefore,
                    project.score));
        }
        Simulation simulation = new Simulation(Solver.totalDurationOfSimulation, order, projects, contributors);
        double f = simulation.getFitness();
        solution.setObjective(0, -f);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(getNumberOfVariables(), getNumberOfObjectives(), getNumberOfConstraints());
        solution.setVariable(0, EncodingUtils.newPermutation(Solver.numberOfProjects));
        for (int i = 0; i < Solver.numberOfProjects; i++) {
            solution.setVariable(i + 1, EncodingUtils.newPermutation(Solver.numberOfContributors));
        }
        return solution;
    }

    @Override
    public void close() {

    }

}