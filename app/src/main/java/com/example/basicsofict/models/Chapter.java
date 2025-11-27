package com.example.basicsofict.models;

import java.io.Serializable;
import java.util.List;

public class Chapter implements Serializable {
    private int id;
    private String title;
    private String description;
    private String aim;
    private List<String> learningObjectives;
    private List<Lesson> lessons;
    private boolean completed;
    private int progressPercentage;

    public Chapter(int id, String title, String description, String aim, List<String> learningObjectives) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.aim = aim;
        this.learningObjectives = learningObjectives;
        this.completed = false;
        this.progressPercentage = 0;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAim() { return aim; }
    public void setAim(String aim) { this.aim = aim; }

    public List<String> getLearningObjectives() { return learningObjectives; }
    public void setLearningObjectives(List<String> learningObjectives) { this.learningObjectives = learningObjectives; }

    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public int getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(int progressPercentage) { this.progressPercentage = progressPercentage; }
}