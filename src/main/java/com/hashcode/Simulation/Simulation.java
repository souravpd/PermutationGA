package com.hashcode.Simulation;

import java.util.*;

/**
 * 
 * 
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
 * /**
 * Input
 * order in which the projects have to executed
 * ArrayList<Project> projects
 * ArrayList<Contributor> contributors
 * int totalDurationOfSimulation
 * Project => (D,S,B,R), ArrayList<ContributorID>
 * 
 */
public class Simulation {

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
}