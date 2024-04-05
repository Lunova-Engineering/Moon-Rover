package com.lunova.moonbot.core.service;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class Person {

    private static final Logger logger = LoggerFactory.getLogger(Person.class);

    @JsonProperty("name")
    private String name;
    @JsonProperty("age")
    private int age;
    @JsonProperty("isEmployed")
    private boolean isEmployed;
    @JsonProperty("salary")
    private double salary;
    @JsonProperty("skills")
    private List<String> skills;

    // Constructor
    @JsonCreator
    public Person(@JsonProperty("name") String name, @JsonProperty("age") int age, @JsonProperty("isEmployed") boolean isEmployed, @JsonProperty("salary") double salary, @JsonProperty("skills") List<String> skills) {
        this.name = name;
        this.age = age;
        this.isEmployed = isEmployed;
        this.salary = salary;
        this.skills = new ArrayList<>(skills);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean employed) {
        isEmployed = employed;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
