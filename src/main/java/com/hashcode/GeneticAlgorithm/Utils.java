package com.hashcode.GeneticAlgorithm;

import java.util.*;

import com.hashcode.Solver;
import com.hashcode.Simulation.Contributor;
import com.hashcode.Simulation.Requirement;

/**
 * Example Solution
 * // Solution
 * // 3
 * // WebServer
 * // Bob Anna
 * // Logging
 * // Anna
 * // WebChat
 * // Maria Bob
 */
/**
 * HashMap<ProjectName, ArrayList<Assignment>>
 * -Weblogger
 * Assignment1
 * -C++
 * -Bob
 * -HTML
 * -Larry
 * Assignment2
 * * -C++
 * -Bob
 * -HTML
 * -Larry
 * 
 */
public class Utils {

    public static int[] randomOrderGenerator() {
        int N = Solver.arrayOfProjects.length;
        int[] newOrder = Arrays.copyOf(Solver.arrayOfProjects, N);
        Random r = new Random();
        for (int i = N - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int temp = newOrder[i];
            newOrder[i] = newOrder[j];
            newOrder[j] = temp;
        }
        return newOrder;
    }

    public static boolean canAssign(Requirement req, Contributor c,
            ArrayList<Contributor> alreadyAssignedContributors) {
        String skill = req.skill;
        int level = req.level;
        if (c.skills.get(skill) >= level) {
            return true;
        } else if (c.skills.get(skill) == level - 1) {
            boolean foundMentor = false;
            for (int i = 0; i < alreadyAssignedContributors.size(); i++) {
                Contributor presentContributor = alreadyAssignedContributors.get(i);
                if (presentContributor.skills.get(skill) >= level) {
                    foundMentor = true;
                    break;
                }
            }
            if (!foundMentor) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // @note Remeber to Shuffle Contributors List
    public static boolean assignContributorToRequirementList(ArrayList<Requirement> requirements,
            int index,
            ArrayList<Contributor> alreadyAssignedContributors, ArrayList<Requirement> satisfiedRequirements,
            HashMap<Integer, Contributor> contributors) {
        // All Requirements have been satisfied
        if (index >= requirements.size()) {
            return true;
        }

        // Satisfy this requirement
        Requirement presentRequirement = requirements.get(index);
        for (Integer contributorID : contributors.keySet()) {
            Contributor c = contributors.get(contributorID);
            Contributor cCopy = new Contributor(c.name, c.id, c.skills);
            if (canAssign(presentRequirement, cCopy, alreadyAssignedContributors)) {
                // This cCopy Contributor has been assigned
                // Update the skill level if required
                String skill = presentRequirement.skill;
                if (cCopy.skills.get(skill) <= presentRequirement.level) {
                    cCopy.skills.put(skill, cCopy.skills.get(skill) + 1);
                }
                // Add this to the alreadyAssignedContributors => To find mentors
                alreadyAssignedContributors.add(cCopy);
                // Put the Updated Contributor to the contributors list
                contributors.put(c.id, cCopy);
                satisfiedRequirements.add(new Requirement(skill, presentRequirement.level, cCopy.id));
                if (assignContributorToRequirementList(requirements, index + 1, alreadyAssignedContributors,
                        satisfiedRequirements, contributors)) {
                    return true;
                }
                alreadyAssignedContributors.remove(alreadyAssignedContributors.size() - 1);
                satisfiedRequirements.remove(satisfiedRequirements.size() - 1);
                contributors.put(c.id, c);
            }
        }
        return false;
    }

    public static void run() {

    }
}
