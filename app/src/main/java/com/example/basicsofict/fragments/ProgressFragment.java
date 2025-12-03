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
import androidx.lifecycle.ViewModelProvider; // <<< IMPORT IS NEEDED
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.basicsofict.DataProvider; // Assuming this is your data source
import com.example.basicsofict.R;
import com.example.basicsofict.adapter.ProgressAdapter;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.utils.ProgressManager;
import com.example.basicsofict.viewmodel.SharedViewModel; // <<< IMPORT IS NEEDED

import java.util.List;
import java.util.Locale;

public class ProgressFragment extends Fragment {

    private ProgressManager progressManager;
    private RecyclerView progressRecyclerView;
    private ProgressAdapter progressAdapter;
    private TextView overallProgressText;
    private ProgressBar overallProgressBar;
    private TextView achievementsCountText;

    private SharedViewModel sharedViewModel; // <<< VARIABLE FOR THE VIEWMODEL

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        // Initialize the manager for saving/loading progress
        progressManager = new ProgressManager(requireContext());
        initializeViews(view);
        setupRecyclerView();

        // ** FIX 1: INITIALIZE THE VIEWMODEL **
        // We get the ViewModel that is scoped to the MainActivity
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // ** FIX 2: SET UP THE OBSERVER **
        // This is the most critical part. We are now "listening" for the update signal.
        sharedViewModel.getProgressUpdateTrigger().observe(getViewLifecycleOwner(), updated -> {
            // The 'updated' variable is the value from LiveData (our 'true' trigger).
            // We check if it's not null to be safe.
            if (updated != null) {
                // When the signal is received, call our update methods.
                updateProgress();
            }
        });

        // Initial update when the view is first created
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
        achievementsCountText.setText(String.format(Locale.getDefault(), "Achievements: %d/10", achievementsCount));
    }

    /**
     * This public method is called to refresh all the UI elements in this fragment.
     */
    public void updateProgress() {
        if (getView() == null) {
            return; // Don't do anything if the view isn't ready
        }
        // Tell the adapter to re-bind all its views, which will update individual chapter progress
        if (progressAdapter != null) {
            progressAdapter.notifyDataSetChanged();
        }
        // Recalculate and display the overall progress at the top
        updateOverallProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Also update when the fragment is resumed, just in case.
        updateProgress();
    }
}
