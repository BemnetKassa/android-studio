package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.basicsofict.DataProvider; // FIX: Use the correct DataProvider
import com.example.basicsofict.MainActivity;
import com.example.basicsofict.R;
import com.example.basicsofict.utils.Event; // FIX: Import Event for observing updates
import com.example.basicsofict.utils.ProgressManager;
import com.example.basicsofict.viewmodel.SharedViewModel; // FIX: Import SharedViewModel

public class HomeFragment extends Fragment {

    private ProgressManager progressManager;
    private TextView tvCompletedLessons, tvOverallProgress;
    private SharedViewModel sharedViewModel; // FIX: Add SharedViewModel

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressManager = new ProgressManager(requireContext());
        initializeViews(view);
        setupClickListeners(view); // This method will be updated

        // FIX: Initialize the ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // FIX: Set up an observer to listen for progress updates from other fragments
        sharedViewModel.getProgressUpdateTrigger().observe(getViewLifecycleOwner(), event -> {
            Boolean content = event.getContentIfNotHandled();
            if (content != null && content) {
                // When a signal is received, update the stats on the home screen
                updateProgressStats();
            }
        });

        // Initial update when view is created
        updateProgressStats();

        return view;
    }

    private void initializeViews(View view) {
        // These IDs must exist in your fragment_home.xml layout
        tvCompletedLessons = view.findViewById(R.id.tv_completed_lessons);
        tvOverallProgress = view.findViewById(R.id.tv_overall_progress);
    }

    private void setupClickListeners(View view) {
        // This listener already existed and is correct
        view.findViewById(R.id.card_continue_learning).setOnClickListener(v -> {
            navigateTo(new ChaptersFragment());
        });

        // This listener already existed and is correct
        view.findViewById(R.id.card_all_chapters).setOnClickListener(v -> {
            navigateTo(new ChaptersFragment());
        });

        // *** FIX: ADD THE MISSING CLICK LISTENERS ***

        // Listener for the "Fun Activities" card
        // Assuming the ID in your XML is 'card_fun_activities'
        View funActivitiesCard = view.findViewById(R.id.card_fun_activities);
        if (funActivitiesCard != null) {
            funActivitiesCard.setOnClickListener(v -> {
                // Navigate to the main activities fragment
                navigateTo(new ActivitiesFragment());
            });
        }

        // Listener for the "Coloring" button inside the fun activities section
        // Assuming the ID is 'btn_coloring_activity'
        View coloringButton = view.findViewById(R.id.card_coloring);
        if (coloringButton != null) {
            coloringButton.setOnClickListener(v -> {
                // Navigate directly to the Coloring fragment
                navigateTo(new ColoringActivityFragment());
            });
        }

        // Listener for the "Games" button inside the fun activities section
        // Assuming the ID is 'btn_games_activity'
        View gamesButton = view.findViewById(R.id.card_games);
        if (gamesButton != null) {
            gamesButton.setOnClickListener(v -> {
                // Navigate to the Drag and Drop fragment as an example game
                navigateTo(new DragDropActivityFragment());
            });
        }
    }

    // Helper method to make navigation cleaner
    private void navigateTo(Fragment fragment) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).loadFragment(fragment, fragment.getClass().getName());
        }
    }

    private void updateProgressStats() {
        int totalLessons = getTotalLessonsCount();
        int completedLessons = getCompletedLessonsCount();
        int progressPercentage = totalLessons > 0 ? (completedLessons * 100) / totalLessons : 0;

        // Ensure views are not null before setting text
        if (tvCompletedLessons != null) {
            tvCompletedLessons.setText(String.valueOf(completedLessons));
        }
        if (tvOverallProgress != null) {
            tvOverallProgress.setText(progressPercentage + "%");
        }
    }

    private int getTotalLessonsCount() {
        int count = 0;
        // FIX: Use DataProvider which is the correct source of data
        for (com.example.basicsofict.models.Chapter chapter : DataProvider.getChapters()) {
            if (chapter.getLessons() != null) {
                count += chapter.getLessons().size();
            }
        }
        return count;
    }


    private int getCompletedLessonsCount() {
        int completed = 0;
        // FIX: Use DataProvider here as well
        for (com.example.basicsofict.models.Chapter chapter : DataProvider.getChapters()) {
            if (chapter.getLessons() != null) {
                for (int i = 0; i < chapter.getLessons().size(); i++) {
                    // Your ProgressManager needs to handle 0-based index for lessons
                    if (progressManager.isLessonCompleted(chapter.getId(), i)) {
                        completed++;
                    }
                }
            }
        }
        return completed;
    }

    // onResume is still a good place to update, in case the app was paused
    @Override
    public void onResume() {
        super.onResume();
        updateProgressStats();
    }
}
