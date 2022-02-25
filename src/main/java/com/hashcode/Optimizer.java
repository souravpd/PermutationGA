package com.hashcode;

import java.util.*;

import com.hashcode.Simulation.Project;
import com.hashcode.Simulation.Contributor;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

//Input
// 3 3
// Anna 1
// C++ 2
// Bob 2
// HTML 5
// CSS 5
// Maria 1
// Python 3
// Logging 5 10 5 1
// C++ 3
// WebServer 7 10 7 2
// HTML 3
// C++ 2
// WebChat 10 20 20 2
// Python 3
// HTML 3

// Solution
// 3
// WebServer
// Bob Anna
// Logging
// Anna
// WebChat
// Maria Bob
class Optimizer implements Problem {

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
        int totalDurationOfSimulation = Solver.totalDurationOfSimulation;
        ArrayList<Project> projects = new ArrayList<Project>();
        HashMap<Integer, Contributor> contributors = new HashMap<Integer, Contributor>();
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(getNumberOfVariables(), getNumberOfObjectives(), getNumberOfConstraints());
        solution.setVariable(0, EncodingUtils.newPermutation(Solver.numberOfProjects));
        for (int i = 0; i < Solver.numberOfProjects; i++) {
            solution.setVariable(i + 1, EncodingUtils.newBinary(Solver.numberOfContributors));
        }
        return solution;
    }

    @Override
    public void close() {

    }

}