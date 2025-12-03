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
// *** Make sure this DataProvider class exists and provides the chapter list ***
import com.example.basicsofict.DataProvider;
import com.example.basicsofict.models.Chapter;
// *** You will likely need a ProgressManager class for progress logic ***
import com.example.basicsofict.utils.ProgressManager; // Assuming you have this class

import java.util.List;
import java.util.Locale;

public class ProgressFragment extends Fragment {

    // Renamed for clarity
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

        // 1. Initialize your progress logic manager
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
        // 2. Get the data
        List<Chapter> chapters = DataProvider.getChapters();

        // 3. Create the adapter with the data and the progress manager
        // This assumes your ProgressAdapter constructor looks like:
        // public ProgressAdapter(List<Chapter> chapters, ProgressManager progressManager)
        progressAdapter = new ProgressAdapter(chapters, progressManager);

        progressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressRecyclerView.setAdapter(progressAdapter);
    }

    private void updateOverallProgress() {
        List<Chapter> chapters = DataProvider.getChapters();

        // 4. Use the progressManager instance to calculate progress
        int overallProgress = progressManager.calculateOverallProgress(chapters);

        overallProgressText.setText(String.format(Locale.getDefault(), "Overall Progress: %d%%", overallProgress));
        overallProgressBar.setProgress(overallProgress);

        int achievementsCount = progressManager.getAchievements().size();
        achievementsCountText.setText(String.format(Locale.getDefault(), "Achievements: %d/10", achievementsCount));
    }

    /**
     * Public method to allow MainActivity to trigger a UI refresh after a lesson is completed.
     */
    public void updateProgress() {
        if (getView() == null) {
            return;
        }

        if (progressAdapter != null) {
            // This tells the RecyclerView to redraw itself
            progressAdapter.notifyDataSetChanged();
        }

        updateOverallProgress();
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
