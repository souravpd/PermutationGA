package com.hashcode;

import java.io.*;
import java.util.*;

import com.hashcode.GeneticAlgorithm.nextPermutation;
import com.hashcode.Simulation.Contributor;
import com.hashcode.Simulation.Project;
import com.hashcode.Simulation.Requirement;
import com.hashcode.Simulation.Simulation;

/**
 * //Input
 * // 3 3
 * // Anna 1
 * // C++ 2
 * // Bob 2
 * // HTML 5
 * // CSS 5
 * // Maria 1
 * // Python 3
 * // Logging 5 10 5 1
 * // C++ 3
 * // WebServer 7 10 7 2
 * // HTML 3
 * // C++ 2
 * // WebChat 10 20 20 2
 * // Python 3
 * // HTML 3
 */
public class Solver {
    public static int numberOfProjects;
    public static int numberOfContributors;
    public static int totalDurationOfSimulation;
    public static ArrayList<String> inputRows = new ArrayList<String>();
    public static ArrayList<Contributor> inputContributors = new ArrayList<Contributor>();
    public static ArrayList<Project> inputProjects = new ArrayList<Project>();
    public static HashMap<Integer, Contributor> contributors = new HashMap<Integer, Contributor>();
    public static int[] arrayOfProjects;

    public static void readInput(String filepath) throws IOException {
        try {
            File file = new File(filepath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                inputRows.add(line);
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseInput() {
        int lineNumber = 0;
        String line[] = inputRows.get(lineNumber++).trim().split(" ");
        numberOfContributors = Integer.parseInt(line[0]);
        numberOfProjects = Integer.parseInt(line[1]);
        HashSet<String> skills = new HashSet<String>();
        int inputContributorID = 0;
        for (int i = 0; i < numberOfContributors; i++) {
            line = inputRows.get(lineNumber++).trim().split(" ");
            String inputcontributorName = line[0];
            int inputN = Integer.parseInt(line[1]);
            HashMap<String, Integer> inputSkillLevels = new HashMap<String, Integer>();
            for (int j = 0; j < inputN; j++) {
                line = inputRows.get(lineNumber++).trim().split(" ");
                inputSkillLevels.put(line[0], Integer.parseInt(line[1]));
                skills.add(line[0]);
            }
            inputContributors.add(new Contributor(inputcontributorName, inputContributorID++, inputSkillLevels));
        }
        for (int i = 0; i < numberOfProjects; i++) {
            line = inputRows.get(lineNumber++).trim().split(" ");
            String inputProjectName = line[0];
            int inputProjectDuration = Integer.parseInt(line[1]);
            int inputProjectScore = Integer.parseInt(line[2]);
            int inputProjectBestBefore = Integer.parseInt(line[3]);
            int inputN = Integer.parseInt(line[4]);
            ArrayList<Requirement> inputRequirementsList = new ArrayList<Requirement>();
            for (int j = 0; j < inputN; j++) {
                line = inputRows.get(lineNumber++).trim().split(" ");
                inputRequirementsList.add(new Requirement(line[0], Integer.parseInt(line[1])));
            }
            inputProjects.add(new Project(inputProjectName, inputRequirementsList, inputProjectDuration,
                    inputProjectBestBefore, inputProjectScore));
        }

        for (int i = 0; i < inputContributors.size(); i++) {
            Contributor inputContributor = inputContributors.get(i);
            for (String skill : skills) {
                if (!inputContributor.skills.containsKey(skill)) {
                    inputContributor.skills.put(skill, 0);
                }
            }
            contributors.put(inputContributor.id, inputContributor);
        }

        arrayOfProjects = new int[Solver.numberOfProjects];
        for (int i = 0; i < numberOfProjects; i++) {
            arrayOfProjects[i] = i;
        }
    }
}
