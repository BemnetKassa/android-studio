package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.basicsofict.R;

public class ActivitiesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        setupClickListeners(view);

        return view;
    }

    private void setupClickListeners(View view) {
        // Multiple Choice Quiz
        view.findViewById(R.id.card_quiz).setOnClickListener(v -> {
            navigateToFragment(new MultipleChoiceFragment());
        });

        // Drag & Drop Puzzle
        view.findViewById(R.id.card_drag_drop).setOnClickListener(v -> {
            navigateToFragment(new DragDropActivityFragment());
        });

        // Coloring Activity
        view.findViewById(R.id.card_coloring).setOnClickListener(v -> {
            navigateToFragment(new ColoringActivityFragment());
        });

        // Typing Practice
        view.findViewById(R.id.card_typing).setOnClickListener(v -> {
            navigateToFragment(new TypingPracticeFragment());
        });
    }

    private void navigateToFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment); // Use your FrameLayout ID
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}