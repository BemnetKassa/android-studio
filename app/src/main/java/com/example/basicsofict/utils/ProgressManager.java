package com.example.basicsofict.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ProgressManager {
    private static final String PREF_NAME = "ICT_Progress";
    private static final String KEY_PROGRESS = "chapter_progress";
    private static final String KEY_COMPLETED = "completed_lessons";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ProgressManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveChapterProgress(int chapterId, int progress) {
        Map<String, Integer> progressMap = getProgressMap();
        progressMap.put("chapter_" + chapterId, progress);
        saveProgressMap(progressMap);
    }

    public int getChapterProgress(int chapterId) {
        Map<String, Integer> progressMap = getProgressMap();
        return progressMap.getOrDefault("chapter_" + chapterId, 0);
    }

    public void markLessonCompleted(int chapterId, int lessonId) {
        Map<String, Boolean> completedMap = getCompletedMap();
        completedMap.put("chapter_" + chapterId + "_lesson_" + lessonId, true);
        saveCompletedMap(completedMap);
    }

    public boolean isLessonCompleted(int chapterId, int lessonId) {
        Map<String, Boolean> completedMap = getCompletedMap();
        return completedMap.getOrDefault("chapter_" + chapterId + "_lesson_" + lessonId, false);
    }

    private Map<String, Integer> getProgressMap() {
        String json = sharedPreferences.getString(KEY_PROGRESS, "");
        if (json.isEmpty()) {
            return new HashMap<>();
        }
        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        return gson.fromJson(json, type);
    }

    private void saveProgressMap(Map<String, Integer> progressMap) {
        String json = gson.toJson(progressMap);
        sharedPreferences.edit().putString(KEY_PROGRESS, json).apply();
    }

    private Map<String, Boolean> getCompletedMap() {
        String json = sharedPreferences.getString(KEY_COMPLETED, "");
        if (json.isEmpty()) {
            return new HashMap<>();
        }
        Type type = new TypeToken<Map<String, Boolean>>(){}.getType();
        return gson.fromJson(json, type);
    }

    private void saveCompletedMap(Map<String, Boolean> completedMap) {
        String json = gson.toJson(completedMap);
        sharedPreferences.edit().putString(KEY_COMPLETED, json).apply();
    }

    public void resetProgress() {
        sharedPreferences.edit().clear().apply();
    }
}