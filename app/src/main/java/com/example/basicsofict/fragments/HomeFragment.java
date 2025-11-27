package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.basicsofict.MainActivity;
import com.example.basicsofict.R;
import com.example.basicsofict.utils.ProgressManager;
import com.example.basicsofict.utils.SampleData;

public class HomeFragment extends Fragment {

    private ProgressManager progressManager;
    private TextView tvCompletedLessons, tvOverallProgress;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressManager = new ProgressManager(requireContext());
        initializeViews(view);
        setupClickListeners(view);
        updateProgressStats();

        return view;
    }

    private void initializeViews(View view) {
        tvCompletedLessons = view.findViewById(R.id.tv_completed_lessons);
        tvOverallProgress = view.findViewById(R.id.tv_overall_progress);
    }

    private void setupClickListeners(View view) {
        view.findViewById(R.id.card_continue_learning).setOnClickListener(v -> {
            // Navigate to chapters fragment
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadFragment(new ChaptersFragment());
            }
        });

        view.findViewById(R.id.card_all_chapters).setOnClickListener(v -> {
            // Navigate to chapters fragment
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadFragment(new ChaptersFragment());
            }
        });
    }

    private void updateProgressStats() {
        int totalLessons = getTotalLessonsCount();
        int completedLessons = getCompletedLessonsCount();
        int progressPercentage = totalLessons > 0 ? (completedLessons * 100) / totalLessons : 0;

        tvCompletedLessons.setText(String.valueOf(completedLessons));
        tvOverallProgress.setText(progressPercentage + "%");
    }

    private int getTotalLessonsCount() {
        int count = 0;
        for (com.example.basicsofict.models.Chapter chapter : SampleData.getSampleChapters()) {
            if (chapter.getLessons() != null) {
                count += chapter.getLessons().size();
            }
        }
        return count;
    }

    private int getCompletedLessonsCount() {
        int completed = 0;
        for (com.example.basicsofict.models.Chapter chapter : SampleData.getSampleChapters()) {
            if (chapter.getLessons() != null) {
                for (int i = 0; i < chapter.getLessons().size(); i++) {
                    if (progressManager.isLessonCompleted(chapter.getId(), i)) {
                        completed++;
                    }
                }
            }
        }
        return completed;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateProgressStats();
    }
}