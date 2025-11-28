package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.basicsofict.R;
import com.example.basicsofict.utils.ProgressManager;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Random;

public class TypingPracticeFragment extends Fragment {

    private TextView tvTextToType, tvStats, tvInstruction;
    private TextInputEditText etUserInput;
    private Button btnCheck;
    private LinearLayout keyboardLayout;

    private String[] practiceTexts = {
            "Hello my name is Kimo",
            "I love using computers",
            "The mouse helps me click",
            "Keyboard has many keys",
            "Monitor shows the screen",
            "Computer lab has rules",
            "Keep your hands clean",
            "Do not eat near computers"
    };

    private int currentTextIndex = 0;
    private long startTime;
    private CountDownTimer timer;
    private int wordsTyped = 0;

    public TypingPracticeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_typing, container, false);

        initializeViews(view);
        setupTypingPractice();
        setupVirtualKeyboard();

        return view;
    }

    private void initializeViews(View view) {
        tvTextToType = view.findViewById(R.id.tv_text_to_type);
        tvStats = view.findViewById(R.id.tv_stats);
        tvInstruction = view.findViewById(R.id.tv_instruction);
        etUserInput = view.findViewById(R.id.et_user_input);
        btnCheck = view.findViewById(R.id.btn_check_typing);
        keyboardLayout = view.findViewById(R.id.keyboard_layout);

        btnCheck.setOnClickListener(v -> checkTyping());

        etUserInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (startTime == 0) {
                    startTime = System.currentTimeMillis();
                    startTimer();
                }
            }
        });
    }

    private void setupTypingPractice() {
        currentTextIndex = new Random().nextInt(practiceTexts.length);
        tvTextToType.setText(practiceTexts[currentTextIndex]);
        etUserInput.setText("");
        startTime = 0;
        wordsTyped = 0;
        updateStats();
    }

    private void setupVirtualKeyboard() {
        keyboardLayout.removeAllViews();

        String[] keyboardRows = {
                "QWERTYUIOP",
                "ASDFGHJKL",
                "ZXCVBNM"
        };

        for (String row : keyboardRows) {
            LinearLayout rowLayout = createKeyboardRow(row);
            keyboardLayout.addView(rowLayout);
        }

        // Add space bar row
        LinearLayout spaceRow = new LinearLayout(requireContext());
        spaceRow.setOrientation(LinearLayout.HORIZONTAL);
        spaceRow.setGravity(android.view.Gravity.CENTER);

        Button spaceBar = createKeyButton("SPACE", 300);
        spaceBar.setOnClickListener(v -> {
            String currentText = etUserInput.getText().toString();
            etUserInput.setText(currentText + " ");
        });

        spaceRow.addView(spaceBar);
        keyboardLayout.addView(spaceRow);
    }

    private LinearLayout createKeyboardRow(String keys) {
        LinearLayout rowLayout = new LinearLayout(requireContext());
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setGravity(android.view.Gravity.CENTER);

        for (char key : keys.toCharArray()) {
            Button keyButton = createKeyButton(String.valueOf(key), 60);
            keyButton.setOnClickListener(v -> {
                String currentText = etUserInput.getText().toString();
                etUserInput.setText(currentText + key);
            });
            rowLayout.addView(keyButton);
        }

        return rowLayout;
    }

    private Button createKeyButton(String text, int width) {
        Button button = new Button(requireContext());
        button.setText(text);
        button.setMinWidth(width);
        button.setMinHeight(getResources().getDimensionPixelSize(R.dimen.key_height));
        button.setTextSize(12);
        button.setAllCaps(false);
        button.setBackgroundResource(R.drawable.key_button_bg);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(2, 2, 2, 2);
        button.setLayoutParams(params);

        return button;
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(300000, 1000) { // 5 minutes max
            public void onTick(long millisUntilFinished) {
                updateStats();
            }

            public void onFinish() {
                updateStats();
            }
        }.start();
    }

    private void updateStats() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = startTime > 0 ? (currentTime - startTime) / 1000 : 0;

        String userText = etUserInput.getText().toString();
        int wordCount = userText.trim().isEmpty() ? 0 : userText.trim().split("\\s+").length;

        String stats = String.format("Words: %d\nTime: %ds", wordCount, elapsedTime);
        tvStats.setText(stats);
    }

    private void checkTyping() {
        String userInput = etUserInput.getText().toString().trim();
        String targetText = practiceTexts[currentTextIndex];

        if (userInput.equalsIgnoreCase(targetText)) {
            tvInstruction.setText("✅ Perfect! You typed it correctly!");
            tvInstruction.setTextColor(getResources().getColor(R.color.primary_color));
            wordsTyped++;

            // MARK ACTIVITY AS COMPLETED WHEN TYPING IS SUCCESSFUL
            markActivityCompleted();

            // Move to next text after delay
            etUserInput.postDelayed(() -> {
                currentTextIndex = (currentTextIndex + 1) % practiceTexts.length;
                tvTextToType.setText(practiceTexts[currentTextIndex]);
                etUserInput.setText("");
                tvInstruction.setText("Type the text shown below:");
                tvInstruction.setTextColor(getResources().getColor(R.color.text_secondary));
            }, 2000);
        } else {
            tvInstruction.setText("❌ Not quite right. Try again!");
            tvInstruction.setTextColor(getResources().getColor(R.color.accent_color));
        }
    }

    // ✅ CORRECTLY PLACED METHOD - OUTSIDE OTHER METHODS BUT INSIDE THE CLASS
    private void markActivityCompleted() {
        ProgressManager progressManager = new ProgressManager(requireContext());
        progressManager.markActivityCompleted("typing_practice"); // Changed to typing_practice

        // Also mark the lesson as completed if this is part of a lesson
        if (getArguments() != null) {
            int chapterId = getArguments().getInt("chapterId", 1);
            int lessonId = getArguments().getInt("lessonId", 1);
            progressManager.markLessonCompleted(chapterId, lessonId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
        // REMOVED THE INCORRECT METHOD FROM HERE
    }
}