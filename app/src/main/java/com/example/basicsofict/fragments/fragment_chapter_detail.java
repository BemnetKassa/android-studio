package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.basicsofict.R;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;
import com.example.basicsofict.utils.ProgressManager;

import java.io.Serializable;

public class ChapterDetailFragment extends Fragment {

    private static final String ARG_CHAPTER = "chapter";

    private Chapter chapter;
    private ProgressManager progressManager;

    public ChapterDetailFragment() {
        // Required empty public constructor
    }

    public static ChapterDetailFragment newInstance(Chapter chapter) {
        ChapterDetailFragment fragment = new ChapterDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHAPTER, (Serializable) chapter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chapter = (Chapter) getArguments().getSerializable(ARG_CHAPTER);
        }
        progressManager = new ProgressManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_detail, container, false);

        if (chapter != null) {
            initializeViews(view);
            setupObjectives(view);
            setupLessons(view);
        }

        return view;
    }

    private void initializeViews(View view) {
        TextView tvTitle = view.findViewById(R.id.tv_chapter_title);
        TextView tvDescription = view.findViewById(R.id.tv_chapter_description);
        TextView tvAim = view.findViewById(R.id.tv_chapter_aim);

        tvTitle.setText(chapter.getTitle());
        tvDescription.setText(chapter.getDescription());
        tvAim.setText(chapter.getAim());
    }

    private void setupObjectives(View view) {
        LinearLayout objectivesContainer = view.findViewById(R.id.objectives_container);
        objectivesContainer.removeAllViews();

        if (chapter.getLearningObjectives() != null) {
            for (String objective : chapter.getLearningObjectives()) {
                TextView objectiveView = new TextView(requireContext());
                objectiveView.setText("• " + objective);
                objectiveView.setTextSize(14);
                objectiveView.setTextColor(getResources().getColor(R.color.text_secondary));
                objectiveView.setPadding(0, 4, 0, 4);
                objectivesContainer.addView(objectiveView);
            }
        }
    }

    private void setupLessons(View view) {
        LinearLayout lessonsContainer = view.findViewById(R.id.lessons_container);
        lessonsContainer.removeAllViews();

        if (chapter.getLessons() != null) {
            for (int i = 0; i < chapter.getLessons().size(); i++) {
                Lesson lesson = chapter.getLessons().get(i);
                View lessonView = createLessonView(lesson, i);
                lessonsContainer.addView(lessonView);
            }
        }
    }

    private View createLessonView(Lesson lesson, int lessonIndex) {
        View lessonView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_lesson, null);

        TextView tvLessonTitle = lessonView.findViewById(R.id.tv_lesson_title);
        TextView tvLessonType = lessonView.findViewById(R.id.tv_lesson_type);
        View completionIndicator = lessonView.findViewById(R.id.completion_indicator);

        tvLessonTitle.setText((lessonIndex + 1) + ". " + lesson.getTitle());

        // Set lesson type with appropriate icon/color
        switch (lesson.getActivityType()) {
            case "theory":
                tvLessonType.setText("📖 Theory");
                break;
            case "practice":
                tvLessonType.setText("🖱️ Practice");
                break;
            case "activity":
                tvLessonType.setText("🎨 Activity");
                break;
            default:
                tvLessonType.setText("📚 Lesson");
        }

        // Check if lesson is completed
        boolean isCompleted = progressManager.isLessonCompleted(chapter.getId(), lessonIndex);
        if (isCompleted) {
            completionIndicator.setBackgroundColor(getResources().getColor(R.color.primary_color));
        } else {
            completionIndicator.setBackgroundColor(getResources().getColor(R.color.progress_pending));
        }

        // Set click listener
        lessonView.setOnClickListener(v -> openLesson(lesson, lessonIndex));

        return lessonView;
    }

    private void openLesson(Lesson lesson, int lessonIndex) {
        // Mark as completed when opened (for demo purposes)
        progressManager.markLessonCompleted(chapter.getId(), lessonIndex);

        // Navigate to lesson detail
        LessonDetailFragment lessonFragment = LessonDetailFragment.newInstance(chapter, lesson, lessonIndex);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).loadFragment(lessonFragment);
        }
    }
}