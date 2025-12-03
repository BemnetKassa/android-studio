package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider; // FIX: Import ViewModelProvider

import com.example.basicsofict.R;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;
import com.example.basicsofict.utils.ProgressManager;
import com.example.basicsofict.viewmodel.SharedViewModel; // FIX: Import SharedViewModel

import java.io.Serializable;

public class LessonDetailFragment extends Fragment {

    private static final String ARG_CHAPTER = "chapter";
    private static final String ARG_LESSON = "lesson";
    private static final String ARG_LESSON_INDEX = "lesson_index";

    private Chapter chapter;
    private Lesson lesson;
    private int lessonIndex;
    private ProgressManager progressManager;
    private SharedViewModel sharedViewModel; // FIX: Add the ViewModel variable

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

        // Initialize the ViewModel, scoped to the MainActivity
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Initialize non-view related objects here
        progressManager = new ProgressManager(requireContext());

        if (getArguments() != null) {
            chapter = (Chapter) getArguments().getSerializable(ARG_CHAPTER);
            lesson = (Lesson) getArguments().getSerializable(ARG_LESSON);
            lessonIndex = getArguments().getInt(ARG_LESSON_INDEX);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_detail, container, false);

        // Re-check arguments here to be safe when the view is recreated.
        if (getArguments() != null) {
            chapter = (Chapter) getArguments().getSerializable(ARG_CHAPTER);
            lesson = (Lesson) getArguments().getSerializable(ARG_LESSON);
            lessonIndex = getArguments().getInt(ARG_LESSON_INDEX);
        }

        // Now, perform the setup.
        if (chapter != null && lesson != null) {
            initializeViews(view);
            setupButtons(view);
        }

        return view;
    }

    private void initializeViews(View view) {
        TextView tvTitle = view.findViewById(R.id.tv_lesson_title);
        TextView tvContent = view.findViewById(R.id.tv_lesson_content);

        if (lesson != null) {
            tvTitle.setText(lesson.getTitle());
            tvContent.setText(lesson.getContent());
        }
    }

    private void setupButtons(View view) {
        Button btnMarkComplete = view.findViewById(R.id.btn_mark_complete);
        Button btnNextLesson = view.findViewById(R.id.btn_next_lesson);

        if (chapter == null) {
            btnMarkComplete.setVisibility(View.GONE);
            btnNextLesson.setVisibility(View.GONE);
            return;
        }

        boolean isCompleted = progressManager.isLessonCompleted(chapter.getId(), lessonIndex);
        if (isCompleted) {
            btnMarkComplete.setText("Completed ✓");
            btnMarkComplete.setEnabled(false);
        }

        btnMarkComplete.setOnClickListener(v -> {
            progressManager.markLessonCompleted(chapter.getId(), lessonIndex);
            btnMarkComplete.setText("Completed ✓");
            btnMarkComplete.setEnabled(false);

            // FIX: Use the ViewModel to send the update signal instead of calling the activity directly
            sharedViewModel.triggerProgressUpdate();
        });

        btnNextLesson.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    // This interface is no longer needed with the ViewModel approach, so it has been removed.
}
