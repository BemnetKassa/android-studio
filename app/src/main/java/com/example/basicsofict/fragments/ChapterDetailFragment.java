package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicsofict.R;
import com.example.basicsofict.adapter.LessonAdapter;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;

import java.io.Serializable;
import java.util.List;

public class ChapterDetailFragment extends Fragment {

    private static final String ARG_CHAPTER = "chapter_arg";

    private Chapter chapter;
    private RecyclerView lessonsRecyclerView;
    private LessonAdapter lessonAdapter;

    public ChapterDetailFragment() {
        // Required empty public constructor
    }

    public static ChapterDetailFragment newInstance(Chapter chapter) {
        ChapterDetailFragment fragment = new ChapterDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHAPTER, chapter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chapter = (Chapter) getArguments().getSerializable(ARG_CHAPTER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_detail, container, false);

        if (chapter != null) {
            // Initialize views
            TextView chapterTitle = view.findViewById(R.id.tvChapterTitle);
            TextView chapterDescription = view.findViewById(R.id.tvChapterDescription);
            lessonsRecyclerView = view.findViewById(R.id.recyclerViewLessons);

            chapterTitle.setText(chapter.getTitle());
            chapterDescription.setText(chapter.getDescription());

            // Setup RecyclerView
            setupLessonsList();
        }

        return view;
    }

    private void setupLessonsList() {
        List<Lesson> lessons = chapter.getLessons();
        if (lessons != null && !lessons.isEmpty()) {
            lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            lessonAdapter = new LessonAdapter(requireContext(), chapter);
            lessonsRecyclerView.setAdapter(lessonAdapter);
        }
    }
}
