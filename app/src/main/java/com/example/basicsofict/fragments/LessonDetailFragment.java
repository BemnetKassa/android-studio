package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.basicsofict.R;
import com.example.basicsofict.MainActivity;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;
import com.example.basicsofict.utils.ProgressManager;

import java.io.Serializable;

public class LessonDetailFragment extends Fragment {

    private static final String ARG_CHAPTER = "chapter";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_LESSON_INDEX = "lesson_index";

    private Chapter chapter;
    private Lesson lesson;
    private int lessonIndex;
    private ProgressManager progressManager;

    public LessonDetailFragment() {
        // Required empty public constructor
    }

    public static LessonDetailFragment newInstance(Chapter chapter, Lesson lesson, int lessonIndex) {
        LessonDetailFragment fragment = new LessonDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHAPTER, (Serializable) chapter);
        args.putSerializable(ARG_LESSON, (Serializable) lesson);
        args.putInt(ARG_LESSON_INDEX, lessonIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chapter = (Chapter) getArguments().getSerializable(ARG_CHAPTER);
            lesson = (Lesson) getArguments().getSerializable(ARG_LESSON);
            lessonIndex = getArguments().getInt(ARG_LESSON_INDEX);
        }
        progressManager = new ProgressManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_detail, container, false);

        if (chapter != null && lesson != null) {
            initializeViews(view);
            setupButtons(view);
        }

        return view;
    }

    private void initializeViews(View view) {
        TextView tvTitle = view.findViewById(R.id.tv_lesson_title);
        TextView tvContent = view.findViewById(R.id.tv_lesson_content);

        tvTitle.setText(lesson.getTitle());
        tvContent.setText(lesson.getContent());
    }

    private void setupButtons(View view) {
        Button btnMarkComplete = view.findViewById(R.id.btn_mark_complete);
        Button btnNextLesson = view.findViewById(R.id.btn_next_lesson);

        // Check if lesson is already completed
        boolean isCompleted = progressManager.isLessonCompleted(chapter.getId(), lessonIndex);
        if (isCompleted) {
            btnMarkComplete.setText("Completed ✓");
            btnMarkComplete.setEnabled(false);
        }

        btnMarkComplete.setOnClickListener(v -> {
            progressManager.markLessonCompleted(chapter.getId(), lessonIndex);
            btnMarkComplete.setText("Completed ✓");
            btnMarkComplete.setEnabled(false);

            // Update progress in home fragment
            if (getActivity() instanceof MainActivity) {
                // You can add a callback here to update the home screen
            }
        });

        btnNextLesson.setOnClickListener(v -> {
            // Navigate to next lesson or back to chapter
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }
}