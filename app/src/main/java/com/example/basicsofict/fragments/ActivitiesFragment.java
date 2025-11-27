package com.example.basicsofict.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.basicsofict.R;
import com.example.basicsofict.MainActivity;

public class ActivitiesFragment extends Fragment {

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        setupActivityCards(view);

        return view;
    }

    private void setupActivityCards(View view) {
        view.findViewById(R.id.card_drag_drop).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadFragment(new DragDropActivityFragment());
            }
        });

        view.findViewById(R.id.card_multiple_choice).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadFragment(new MultipleChoiceFragment());
            }
        });

        view.findViewById(R.id.card_coloring).setOnClickListener(v -> {
            // TODO: Implement coloring activity in Phase 5
            if (getActivity() instanceof MainActivity) {
                // Placeholder for now
            }
        });

        view.findViewById(R.id.card_typing).setOnClickListener(v -> {
            // TODO: Implement typing practice in Phase 5
            if (getActivity() instanceof MainActivity) {
                // Placeholder for now
            }
        });
    }
}