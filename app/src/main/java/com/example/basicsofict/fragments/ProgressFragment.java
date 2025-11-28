// ProgressFragment.java - UPDATE EXISTING FILE
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
import com.example.basicsofict.utils.ProgressManager;
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
        List<Chapter> chapters = getChaptersFromDataSource();
        progressAdapter = new ProgressAdapter(chapters, progressManager);
        progressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressRecyclerView.setAdapter(progressAdapter);
    }

    private void updateOverallProgress() {
        List<Chapter> chapters = getChaptersFromDataSource();
        int overallProgress = progressManager.calculateOverallProgress(chapters);

        overallProgressText.setText(String.format("Overall Progress: %d%%", overallProgress));
        overallProgressBar.setProgress(overallProgress);

        int achievementsCount = progressManager.getAchievements().size();
        achievementsCountText.setText(String.format("Achievements: %d/10", achievementsCount));
    }

    private List<Chapter> getChaptersFromDataSource() {
        // Use your existing chapter data source
        return Chapter.getSampleChapters(requireContext());
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