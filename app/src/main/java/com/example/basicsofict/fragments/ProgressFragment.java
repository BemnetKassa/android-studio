package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.basicsofict.R;
import com.example.basicsofict.adapter.ProgressAdapter;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;
import com.example.basicsofict.utils.ProgressManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgressFragment extends Fragment {

    private ProgressManager progressManager;
    private RecyclerView progressRecyclerView;
    private ProgressAdapter progressAdapter;
    private TextView overallProgressText;
    private ProgressBar overallProgressBar;
    private TextView achievementsCountText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        progressManager = new ProgressManager(requireContext());
        initializeViews(view);
        setupRecyclerView();
        updateOverallProgress();

        return view;
    }

    private void initializeViews(View view) {
        progressRecyclerView = view.findViewById(R.id.progressRecyclerView);
        overallProgressText = view.findViewById(R.id.overallProgressText);
        overallProgressBar = view.findViewById(R.id.overallProgressBar);
        achievementsCountText = view.findViewById(R.id.achievementsCountText);
    }

    private void setupRecyclerView() {
        List<Chapter> chapters = createSampleChapters();
        progressAdapter = new ProgressAdapter(chapters, progressManager);
        progressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressRecyclerView.setAdapter(progressAdapter);
    }

    private void updateOverallProgress() {
        List<Chapter> chapters = createSampleChapters();
        int overallProgress = progressManager.calculateOverallProgress(chapters);

        overallProgressText.setText(String.format("Overall Progress: %d%%", overallProgress));
        overallProgressBar.setProgress(overallProgress);

        int achievementsCount = progressManager.getAchievements().size();
        achievementsCountText.setText(String.format("Achievements: %d/10", achievementsCount));
    }

    private List<Chapter> createSampleChapters() {
        List<Chapter> chapters = new ArrayList<>();

        // Chapter 1: Working safely with computers
        Chapter chapter1 = new Chapter(1,
                "Working safely with computers",
                "Learn computer lab safety rules",
                "Understand how to use computers safely",
                Arrays.asList("Follow computer lab rules", "Identify potential risks", "Practice safe computer usage")
        );
        List<Lesson> lessons1 = new ArrayList<>();
        lessons1.add(new Lesson("Rules and regulations", "Learn about computer lab rules and why they are important", "theory"));
        lessons1.add(new Lesson("Risks in computer lab", "Identify potential dangers and how to avoid them", "theory"));
        lessons1.add(new Lesson("Safe practices", "Learn proper computer usage habits", "practice"));
        chapter1.setLessons(lessons1);

        // Chapter 2: Computer hardware
        Chapter chapter2 = new Chapter(2,
                "Computer hardware",
                "Learn about computer parts and their functions",
                "Identify basic computer components",
                Arrays.asList("Identify computer parts", "Understand their functions", "Connect basic components")
        );
        List<Lesson> lessons2 = new ArrayList<>();
        lessons2.add(new Lesson("Monitor and CPU", "Learn about the screen and main computer unit", "theory"));
        lessons2.add(new Lesson("Keyboard and mouse", "Understand input devices", "interactive"));
        lessons2.add(new Lesson("Other peripherals", "Learn about printers, speakers, and more", "theory"));
        chapter2.setLessons(lessons2);

        // Chapter 3: Using the keyboard
        Chapter chapter3 = new Chapter(3,
                "Using the keyboard",
                "Learn keyboard basics and typing",
                "Develop basic keyboard skills",
                Arrays.asList("Identify keyboard keys", "Learn proper finger placement", "Practice typing")
        );
        List<Lesson> lessons3 = new ArrayList<>();
        lessons3.add(new Lesson("Keyboard layout", "Understand QWERTY keyboard arrangement", "theory"));
        lessons3.add(new Lesson("Special keys", "Learn about Enter, Space, Backspace keys", "interactive"));
        lessons3.add(new Lesson("Typing practice", "Practice typing words and sentences", "practice"));
        chapter3.setLessons(lessons3);

        // Chapter 4: Using the mouse
        Chapter chapter4 = new Chapter(4,
                "Using the mouse",
                "Learn mouse functions and operations",
                "Master basic mouse skills",
                Arrays.asList("Hold mouse properly", "Practice clicking", "Learn dragging and dropping")
        );
        List<Lesson> lessons4 = new ArrayList<>();
        lessons4.add(new Lesson("Mouse parts", "Learn about left/right buttons and scroll wheel", "theory"));
        lessons4.add(new Lesson("Clicking practice", "Practice single and double clicking", "practice"));
        lessons4.add(new Lesson("Drag and drop", "Learn to move objects on screen", "interactive"));
        chapter4.setLessons(lessons4);

        // Add more chapters as needed...
        chapters.add(chapter1);
        chapters.add(chapter2);
        chapters.add(chapter3);
        chapters.add(chapter4);

        return chapters;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when fragment becomes visible
        if (progressAdapter != null) {
            progressAdapter.notifyDataSetChanged();
            updateOverallProgress();
        }
    }


}