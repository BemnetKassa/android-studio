package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.basicsofict.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.example.basicsofict.MainActivity;
import com.example.basicsofict.utils.ProgressManager;

public class MultipleChoiceFragment extends Fragment {

    private TextView tvQuestion, tvFeedback;
    private LinearLayout optionsContainer;
    private Button btnNext;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public MultipleChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_multiple_choice, container, false);

        initializeViews(view);
        loadQuestions();
        showQuestion(currentQuestionIndex);

        return view;
    }

    private void initializeViews(View view) {
        tvQuestion = view.findViewById(R.id.tv_question);
        tvFeedback = view.findViewById(R.id.tv_feedback);
        optionsContainer = view.findViewById(R.id.options_container);
        btnNext = view.findViewById(R.id.btn_next_question);

        btnNext.setOnClickListener(v -> showNextQuestion());
    }

    private void loadQuestions() {
        questions = new ArrayList<>();

        // Add ICT-related questions
        questions.add(new Question(
                "What should you do before using a computer?",
                new String[]{"Wash your hands", "Eat a snack", "Run around", "Shout loudly"},
                0 // Correct answer index
        ));

        questions.add(new Question(
                "Which part of the computer shows what you are doing?",
                new String[]{"Keyboard", "Monitor", "Mouse", "CPU"},
                1
        ));

        questions.add(new Question(
                "What is used to type letters and numbers?",
                new String[]{"Mouse", "Monitor", "Keyboard", "Printer"},
                2
        ));

        questions.add(new Question(
                "Where should you keep food and drinks in the computer lab?",
                new String[]{"On the computer", "Away from computers", "Under the table", "In your bag"},
                1
        ));

        questions.add(new Question(
                "How should you sit in front of a computer?",
                new String[]{"Slouching", "With straight back", "Lying down", "Standing up"},
                1
        ));
    }

    private void showQuestion(int index) {
        if (index >= questions.size()) {
            showResults();
            return;
        }

        Question question = questions.get(index);
        tvQuestion.setText(question.getQuestion());
        optionsContainer.removeAllViews();
        tvFeedback.setVisibility(View.GONE);
        btnNext.setEnabled(false);

        // Shuffle options
        List<String> options = new ArrayList<>();
        for (String option : question.getOptions()) {
            options.add(option);
        }
        Collections.shuffle(options);

        for (int i = 0; i < options.size(); i++) {
            Button optionButton = createOptionButton(options.get(i), i, question);
            optionsContainer.addView(optionButton);
        }
    }

    private Button createOptionButton(String option, int index, Question question) {
        Button button = new Button(requireContext());
        button.setText(option);
        button.setBackgroundResource(R.drawable.option_button_bg);
        button.setTextColor(getResources().getColor(R.color.text_primary));
        button.setTextSize(14);
        button.setPadding(32, 16, 32, 16);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        button.setLayoutParams(params);

        button.setOnClickListener(v -> {
            checkAnswer(option, question, button);
        });

        return button;
    }

    private void checkAnswer(String selectedOption, Question question, Button selectedButton) {
        // Disable all options
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            View child = optionsContainer.getChildAt(i);
            if (child instanceof Button) {
                child.setEnabled(false);
            }
        }

        String correctAnswer = question.getOptions()[question.getCorrectAnswer()];

        if (selectedOption.equals(correctAnswer)) {
            selectedButton.setBackgroundResource(R.drawable.option_correct_bg);
            tvFeedback.setText("✅ Correct! Well done!");
            tvFeedback.setTextColor(getResources().getColor(R.color.primary_color));
            score++;
        } else {
            selectedButton.setBackgroundResource(R.drawable.option_wrong_bg);
            tvFeedback.setText("❌ Incorrect. The correct answer is: " + correctAnswer);
            tvFeedback.setTextColor(getResources().getColor(R.color.accent_color));

            // Highlight correct answer
            for (int i = 0; i < optionsContainer.getChildCount(); i++) {
                View child = optionsContainer.getChildAt(i);
                if (child instanceof Button) {
                    Button btn = (Button) child;
                    if (btn.getText().toString().equals(correctAnswer)) {
                        btn.setBackgroundResource(R.drawable.option_correct_bg);
                    }
                }
            }
        }

        tvFeedback.setVisibility(View.VISIBLE);
        btnNext.setEnabled(true);
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        showQuestion(currentQuestionIndex);
    }

    private void showResults() {
        tvQuestion.setText("Quiz Completed!");
        optionsContainer.removeAllViews();
        tvFeedback.setText("Your score: " + score + "/" + questions.size());
        tvFeedback.setVisibility(View.VISIBLE);
        btnNext.setText("Finish");
        btnNext.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }

    // Question model class
    private static class Question {
        private String question;
        private String[] options;
        private int correctAnswer;

        public Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() { return question; }
        public String[] getOptions() { return options; }
        public int getCorrectAnswer() { return correctAnswer; }
    }
    // Add to the end of each activity fragment when activity is completed
    private void markActivityCompleted() {
        ProgressManager progressManager = new ProgressManager(requireContext());
        progressManager.markActivityCompleted("multiple_choice"); // Change for each activity type

        // Also mark the lesson as completed if this is part of a lesson
        if (getArguments() != null) {
            int chapterId = getArguments().getInt("chapterId", 1);
            int lessonId = getArguments().getInt("lessonId", 1);
            progressManager.markLessonCompleted(chapterId, lessonId);
        }
    }
}