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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicsofict.DataProvider;
import com.example.basicsofict.R;
import com.example.basicsofict.adapter.ProgressAdapter;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.utils.Event; // <<< IMPORT EVENT
import com.example.basicsofict.utils.ProgressManager;
import com.example.basicsofict.viewmodel.SharedViewModel;

import java.util.List;
import java.util.Locale;

public class ProgressFragment extends Fragment {

    private ProgressManager progressManager;
    private RecyclerView progressRecyclerView;
    private ProgressAdapter progressAdapter;
    private TextView overallProgressText;
    private ProgressBar overallProgressBar;
    private TextView achievementsCountText;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        progressManager = new ProgressManager(requireContext());
        initializeViews(view);
        setupRecyclerView();

        // Get the ViewModel scoped to the MainActivity
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // *** THE FINAL FIX: OBSERVE THE EVENT CORRECTLY ***
        sharedViewModel.getProgressUpdateTrigger().observe(getViewLifecycleOwner(), event -> {
            // Get the content of the event. It will be null if it has already been handled.
            Boolean content = event.getContentIfNotHandled();
            if (content != null && content) {
                // If the event is fresh, update the progress.
                updateProgress();
            }
        });

        return view;
    }

    // You had an `onResume` which is good, but let's make sure it's also up-to-date
    // when the view is first created.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Do an initial update as soon as the view is created and ready.
        updateProgress();
    }

    private void initializeViews(View view) {
        progressRecyclerView = view.findViewById(R.id.progressRecyclerView);
        overallProgressText = view.findViewById(R.id.overallProgressText);
        overallProgressBar = view.findViewById(R.id.overallProgressBar);
        achievementsCountText = view.findViewById(R.id.achievementsCountText);
    }

    private void setupRecyclerView() {
        List<Chapter> chapters = DataProvider.getChapters();
        progressAdapter = new ProgressAdapter(chapters, progressManager);
        progressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressRecyclerView.setAdapter(progressAdapter);
    }

    private void updateOverallProgress() {
        List<Chapter> chapters = DataProvider.getChapters();
        int overallProgress = progressManager.calculateOverallProgress(chapters);
        overallProgressText.setText(String.format(Locale.getDefault(), "Overall Progress: %d%%", overallProgress));
        overallProgressBar.setProgress(overallProgress);

        int achievementsCount = progressManager.getAchievements().size();
        // This view ID is from your layout XML
        TextView badgesText = getView().findViewById(R.id.achievementsCountText);
        if(badgesText != null) {
            badgesText.setText(String.valueOf(achievementsCount));
        }

        // Also update the total lessons and chapters completed text
        int totalCompletedLessons = 0;
        int totalCompletedChapters = 0;
        int totalChapters = chapters.size();
        for(Chapter chapter : chapters) {
            int chapterProgress = progressManager.calculateChapterProgress(chapter);
            if (chapterProgress == 100) {
                totalCompletedChapters++;
            }
            if(chapter.getLessons() != null) {
                totalCompletedLessons += (chapter.getLessons().size() * chapterProgress) / 100;
            }
        }

        TextView lessonsCompletedView = getView().findViewById(R.id.lessonsCompletedText);
        if(lessonsCompletedView != null) {
            lessonsCompletedView.setText(String.valueOf(totalCompletedLessons));
        }
        TextView chaptersCompletedView = getView().findViewById(R.id.chaptersCompletedText);
        if(chaptersCompletedView != null) {
            chaptersCompletedView.setText(String.valueOf(totalCompletedChapters));
        }
    }

    public void updateProgress() {
        if (getView() == null) {
            return;
        }
        if (progressAdapter != null) {
            progressAdapter.notifyDataSetChanged();
        }
        updateOverallProgress();
    }

    // We still keep onResume for cases where the app is paused and resumed.
    @Override
    public void onResume() {
        super.onResume();
        updateProgress();
    }
}
