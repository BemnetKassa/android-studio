package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicsofict.MainActivity;
import com.example.basicsofict.R;
import com.example.basicsofict.adapter.ChapterAdapter;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.utils.ProgressManager;
import com.example.basicsofict.utils.SampleData;
import java.util.List;

public class ChaptersFragment extends Fragment implements ChapterAdapter.OnChapterClickListener {

    private RecyclerView recyclerView;
    private ChapterAdapter adapter;
    private ProgressManager progressManager;
    private List<Chapter> chapters;

    public ChaptersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapters, container, false);

        progressManager = new ProgressManager(requireContext());
        chapters = SampleData.getSampleChapters();

        initializeRecyclerView(view);

        return view;
    }

    private void initializeRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_chapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ChapterAdapter(chapters, progressManager, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onChapterClick(Chapter chapter) {
        // TODO: Navigate to chapter details (Phase 3)
        // For now, show a simple message or implement basic navigation
        if (getActivity() instanceof MainActivity) {
            // We'll implement chapter details in Phase 3
        }
    }
}