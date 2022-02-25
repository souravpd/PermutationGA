package com.hashcode.Simulation;

import java.util.*;

public class Contributor {

    public String name;
    public int id;
    public HashMap<String, Integer> skills;

    public Contributor(String name, int id, HashMap<String, Integer> skills) {
        this.name = name;
        this.id = id;
        this.skills = skills;
    }

}
