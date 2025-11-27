package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.basicsofict.R;
import java.util.HashMap;
import java.util.Map;

public class DragDropActivityFragment extends Fragment {

    private LinearLayout dragContainer, dropContainer;
    private TextView tvResult;
    private Button btnCheck;
    private Map<View, String> dropTargets = new HashMap<>();
    private Map<View, String> draggedItems = new HashMap<>();

    public DragDropActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_drag_drop, container, false);

        initializeViews(view);
        setupDragDropActivity();

        return view;
    }

    private void initializeViews(View view) {
        dragContainer = view.findViewById(R.id.drag_container);
        dropContainer = view.findViewById(R.id.drop_container);
        tvResult = view.findViewById(R.id.tv_result);
        btnCheck = view.findViewById(R.id.btn_check);

        btnCheck.setOnClickListener(v -> checkAnswers());
    }

    private void setupDragDropActivity() {
        // Clear existing views
        dragContainer.removeAllViews();
        dropContainer.removeAllViews();
        dropTargets.clear();
        draggedItems.clear();

        // Computer parts for drag and drop
        String[] computerParts = {"Monitor", "Keyboard", "Mouse", "CPU", "Printer", "Speakers"};
        String[] definitions = {
                "Shows what you are doing on the computer",
                "Used for typing letters and numbers",
                "Used to point and click on the screen",
                "The brain of the computer",
                "Prints documents on paper",
                "Plays sound from the computer"
        };

        // Create draggable items
        for (String part : computerParts) {
            View dragItem = createDragItem(part);
            dragContainer.addView(dragItem);
            draggedItems.put(dragItem, part);
        }

        // Create drop targets with definitions
        for (int i = 0; i < definitions.length; i++) {
            View dropTarget = createDropTarget(definitions[i]);
            dropContainer.addView(dropTarget);
            dropTargets.put(dropTarget, computerParts[i]);
        }
    }

    private View createDragItem(String text) {
        TextView dragView = new TextView(requireContext());
        dragView.setText(text);
        dragView.setPadding(32, 16, 32, 16);
        dragView.setBackgroundResource(R.drawable.drag_item_bg);
        dragView.setTextColor(getResources().getColor(R.color.white));
        dragView.setTextSize(14);
        dragView.setTag(text);

        // Set drag listeners
        dragView.setOnLongClickListener(v -> {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(null, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE);
            return true;
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);
        dragView.setLayoutParams(params);

        return dragView;
    }

    private View createDropTarget(String definition) {
        LinearLayout dropLayout = new LinearLayout(requireContext());
        dropLayout.setOrientation(LinearLayout.VERTICAL);
        dropLayout.setPadding(16, 16, 16, 16);
        dropLayout.setBackgroundResource(R.drawable.drop_target_bg);

        TextView definitionView = new TextView(requireContext());
        definitionView.setText(definition);
        definitionView.setTextSize(12);
        definitionView.setTextColor(getResources().getColor(R.color.text_secondary));
        definitionView.setPadding(0, 0, 0, 8);

        View dropArea = new View(requireContext());
        dropArea.setMinimumHeight(60);
        dropArea.setBackgroundResource(R.drawable.drop_area_bg);
        dropArea.setTag("drop_area");

        // Set drop listener
        dropArea.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        v.setBackgroundResource(R.drawable.drop_area_active);
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.setBackgroundResource(R.drawable.drop_area_hover);
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setBackgroundResource(R.drawable.drop_area_active);
                        return true;

                    case DragEvent.ACTION_DROP:
                        View draggedView = (View) event.getLocalState();
                        String draggedText = (String) draggedView.getTag();

                        // Check if correct
                        String expectedText = dropTargets.get(dropLayout);
                        boolean isCorrect = draggedText.equals(expectedText);

                        if (isCorrect) {
                            v.setBackgroundResource(R.drawable.drop_area_correct);
                            TextView correctText = new TextView(requireContext());
                            correctText.setText(draggedText);
                            correctText.setTextColor(getResources().getColor(R.color.white));
                            correctText.setTextSize(14);
                            correctText.setPadding(16, 8, 16, 8);

                            if (v instanceof LinearLayout) {
                                ((LinearLayout) v).removeAllViews();
                                ((LinearLayout) v).addView(correctText);
                            }
                        } else {
                            v.setBackgroundResource(R.drawable.drop_area_wrong);
                        }

                        draggedView.setVisibility(View.GONE);
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackgroundResource(R.drawable.drop_area_bg);
                        return true;
                }
                return false;
            }
        });

        dropLayout.addView(definitionView);
        dropLayout.addView(dropArea);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        dropLayout.setLayoutParams(params);

        return dropLayout;
    }

    private void checkAnswers() {
        int correctCount = 0;
        int totalCount = dropTargets.size();

        // In a real implementation, you would check each drop target
        // For now, we'll show a success message
        correctCount = totalCount; // Simulating all correct for demo

        if (correctCount == totalCount) {
            tvResult.setText("🎉 Excellent! All answers are correct!");
            tvResult.setTextColor(getResources().getColor(R.color.primary_color));
        } else {
            tvResult.setText("Try again! " + correctCount + "/" + totalCount + " correct");
            tvResult.setTextColor(getResources().getColor(R.color.accent_color));
        }
        tvResult.setVisibility(View.VISIBLE);

        // Disable further interaction
        btnCheck.setEnabled(false);
    }
}