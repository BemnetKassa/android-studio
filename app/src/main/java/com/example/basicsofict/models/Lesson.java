package com.example.ictgrade1learning.models;

public class Lesson {
    private String title;
    private String content;
    private String activityType; // "theory", "practice", "interactive"
    private boolean completed;
    private String imageResource;
    private String videoResource;

    public Lesson(String title, String content, String activityType) {
        this.title = title;
        this.content = content;
        this.activityType = activityType;
        this.completed = false;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getImageResource() { return imageResource; }
    public void setImageResource(String imageResource) { this.imageResource = imageResource; }

    public String getVideoResource() { return videoResource; }
    public void setVideoResource(String videoResource) { this.videoResource = videoResource; }
}