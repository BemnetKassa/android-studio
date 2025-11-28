// ProgressManager.java - NEW FILE in utils package
package com.example.basicsofict.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProgressManager {
    private static final String PREF_NAME = "ICT_Progress";
    private static final String KEY_LESSON_PROGRESS = "lesson_progress";
    private static final String KEY_ACHIEVEMENTS = "achievements";
    private static final String KEY_ACTIVITY_COMPLETIONS = "activity_completions";

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Context context;

    public ProgressManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Lesson progress methods
    public void markLessonCompleted(int chapterId, int lessonId) {
        String key = chapterId + "_" + lessonId;
        Map<String, Boolean> lessonMap = getLessonProgressMap();
        lessonMap.put(key, true);
        saveLessonProgressMap(lessonMap);

        checkAchievements();
    }

    public boolean isLessonCompleted(int chapterId, int lessonId) {
        String key = chapterId + "_" + lessonId;
        Map<String, Boolean> lessonMap = getLessonProgressMap();
        return lessonMap.getOrDefault(key, false);
    }

    // Activity completion tracking
    public void markActivityCompleted(String activityType) {
        Map<String, Integer> activityMap = getActivityCompletionsMap();
        int currentCount = activityMap.getOrDefault(activityType, 0);
        activityMap.put(activityType, currentCount + 1);
        saveActivityCompletionsMap(activityMap);
    }

    public int getActivityCompletionCount(String activityType) {
        Map<String, Integer> activityMap = getActivityCompletionsMap();
        return activityMap.getOrDefault(activityType, 0);
    }

    // Achievement methods
    public void unlockAchievement(String achievementId) {
        Set<String> achievements = getAchievements();
        if (achievements.add(achievementId)) {
            sharedPreferences.edit().putStringSet(KEY_ACHIEVEMENTS, achievements).apply();

            // Show achievement notification
            showAchievementNotification(achievementId);
        }
    }

    public Set<String> getAchievements() {
        return sharedPreferences.getStringSet(KEY_ACHIEVEMENTS, new HashSet<>());
    }

    // Progress calculation
    public int calculateOverallProgress(List<Chapter> chapters) {
        if (chapters == null || chapters.isEmpty()) return 0;

        int totalLessons = 0;
        int completedLessons = 0;

        for (Chapter chapter : chapters) {
            for (Lesson lesson : chapter.getLessons()) {
                totalLessons++;
                if (isLessonCompleted(chapter.getId(), lesson.getId())) {
                    completedLessons++;
                }
            }
        }

        return totalLessons > 0 ? (completedLessons * 100) / totalLessons : 0;
    }

    public int calculateChapterProgress(Chapter chapter) {
        int completedLessons = 0;
        for (Lesson lesson : chapter.getLessons()) {
            if (isLessonCompleted(chapter.getId(), lesson.getId())) {
                completedLessons++;
            }
        }
        int totalLessons = chapter.getLessons().size();
        return totalLessons > 0 ? (completedLessons * 100) / totalLessons : 0;
    }

    // Achievement checking
    private void checkAchievements() {
        // Check for various achievements
        checkCompletionAchievements();
        checkActivityAchievements();
    }

    private void checkCompletionAchievements() {
        // Implement based on your specific achievement criteria
        int completedLessons = getTotalCompletedLessons();

        if (completedLessons >= 1 && !getAchievements().contains("first_lesson")) {
            unlockAchievement("first_lesson");
        }
        if (completedLessons >= 5 && !getAchievements().contains("fast_learner")) {
            unlockAchievement("fast_learner");
        }
        if (completedLessons >= 10 && !getAchievements().contains("ict_expert")) {
            unlockAchievement("ict_expert");
        }
    }

    private void checkActivityAchievements() {
        // Check activity-based achievements
        Map<String, Integer> activities = getActivityCompletionsMap();

        if (activities.getOrDefault("multiple_choice", 0) >= 3 &&
                !getAchievements().contains("quiz_master")) {
            unlockAchievement("quiz_master");
        }

        if (activities.getOrDefault("coloring", 0) >= 5 &&
                !getAchievements().contains("artist")) {
            unlockAchievement("artist");
        }
    }

    private int getTotalCompletedLessons() {
        Map<String, Boolean> lessonMap = getLessonProgressMap();
        int count = 0;
        for (boolean completed : lessonMap.values()) {
            if (completed) count++;
        }
        return count;
    }

    private void showAchievementNotification(String achievementId) {
        // You can implement a Toast or custom notification
        android.widget.Toast.makeText(context,
                "Achievement Unlocked: " + getAchievementName(achievementId),
                android.widget.Toast.LENGTH_LONG).show();
    }

    private String getAchievementName(String achievementId) {
        switch (achievementId) {
            case "first_lesson": return "First Lesson Complete!";
            case "fast_learner": return "Fast Learner!";
            case "ict_expert": return "ICT Expert!";
            case "quiz_master": return "Quiz Master!";
            case "artist": return "Young Artist!";
            default: return "Achievement Unlocked!";
        }
    }

    // Helper methods for SharedPreferences
    private Map<String, Boolean> getLessonProgressMap() {
        String json = sharedPreferences.getString(KEY_LESSON_PROGRESS, "{}");
        Type type = new TypeToken<Map<String, Boolean>>(){}.getType();
        Map<String, Boolean> map = gson.fromJson(json, type);
        return map != null ? map : new HashMap<>();
    }

    private void saveLessonProgressMap(Map<String, Boolean> map) {
        String json = gson.toJson(map);
        sharedPreferences.edit().putString(KEY_LESSON_PROGRESS, json).apply();
    }

    private Map<String, Integer> getActivityCompletionsMap() {
        String json = sharedPreferences.getString(KEY_ACTIVITY_COMPLETIONS, "{}");
        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        Map<String, Integer> map = gson.fromJson(json, type);
        return map != null ? map : new HashMap<>();
    }

    private void saveActivityCompletionsMap(Map<String, Integer> map) {
        String json = gson.toJson(map);
        sharedPreferences.edit().putString(KEY_ACTIVITY_COMPLETIONS, json).apply();
    }

    // Reset progress (for testing)
    public void resetAllProgress() {
        sharedPreferences.edit().clear().apply();
    }
}