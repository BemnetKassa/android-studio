package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class ChapterDetailFragment extends Fragment {

    private Chapter chapter;
    private RecyclerView recyclerViewLessons;
    private LessonAdapter lessonAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_detail, container, false);

        // Get chapter data from arguments
        if (getArguments() != null) {
            chapter = (Chapter) getArguments().getSerializable("chapter");
        }

        initializeViews(view);
        setupRecyclerView();

        return view;
    }

    private void initializeViews(View view) {
        // Use the EXACT IDs from your fragment_chapter_detail.xml
        TextView tvTitle = view.findViewById(R.id.tvChapterTitle);
        TextView tvDescription = view.findViewById(R.id.tvChapterDescription);
        TextView tvAim = view.findViewById(R.id.tvChapterAim);
        recyclerViewLessons = view.findViewById(R.id.recyclerViewLessons);

        if (chapter != null) {
            tvTitle.setText(chapter.getTitle());
            tvDescription.setText(chapter.getDescription());
            tvAim.setText("Aim: " + chapter.getAim());
        }
    }

    private void setupRecyclerView() {
        if (chapter != null && chapter.getLessons() != null) {
            List<Lesson> lessons = chapter.getLessons();
            lessonAdapter = new LessonAdapter(lessons, new LessonAdapter.OnLessonClickListener() {
                @Override
                public void onLessonClick(Lesson lesson) {
                    // Handle lesson click - navigate to lesson detail or activity
                    navigateToLesson(lesson);
                }
            });

            recyclerViewLessons.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewLessons.setAdapter(lessonAdapter);
        }
    }

    private void navigateToLesson(Lesson lesson) {
        // Navigate to lesson detail or activity based on lesson type
        Fragment fragment = null;

        if (lesson.getActivityType() != null) {
            switch (lesson.getActivityType()) {
                case "theory":
                    // Navigate to lesson content fragment
                    fragment = new LessonDetailFragment();
                    break;
                case "practice":
                case "interactive":
                    // Navigate to appropriate activity
                    if (lesson.getTitle().toLowerCase().contains("coloring")) {
                        fragment = new ColoringActivityFragment();
                    } else if (lesson.getTitle().toLowerCase().contains("drag") || lesson.getTitle().toLowerCase().contains("drop")) {
                        fragment = new DragDropActivityFragment();
                    } else if (lesson.getTitle().toLowerCase().contains("typing") || lesson.getTitle().toLowerCase().contains("keyboard")) {
                        fragment = new TypingPracticeFragment();
                    } else {
                        fragment = new MultipleChoiceFragment();
                    }
                    break;
            }
        }

        if (fragment != null && getActivity() != null) {
            // Pass lesson data to the fragment
            Bundle args = new Bundle();
            args.putSerializable("lesson", lesson);
            if (chapter != null) {
                args.putInt("chapterId", chapter.getId());
                args.putInt("lessonId", chapter.getLessons().indexOf(lesson) + 1);
            }
            fragment.setArguments(args);

            // Navigate to the fragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}