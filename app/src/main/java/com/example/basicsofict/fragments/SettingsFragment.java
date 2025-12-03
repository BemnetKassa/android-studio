package com.example.basicsofict.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.basicsofict.R;
import com.example.basicsofict.utils.ProgressManager;
import com.example.basicsofict.viewmodel.SharedViewModel;

public class SettingsFragment extends Fragment {

    private ProgressManager progressManager;
    private SharedViewModel sharedViewModel;
    private Button btnResetProgress;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ProgressManager and SharedViewModel
        progressManager = new ProgressManager(requireContext());
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Find the button from the layout
        btnResetProgress = view.findViewById(R.id.btn_reset_progress);

        // Set a click listener on the button
        btnResetProgress.setOnClickListener(v -> {
            // Show a confirmation dialog before resetting
            showResetConfirmationDialog();
        });

        return view;
    }

    private void showResetConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Reset Progress")
                .setMessage("Are you sure you want to reset all your learning progress? This action cannot be undone.")
                .setPositiveButton("Reset", (dialog, which) -> {
                    // User confirmed, so reset the progress
                    resetProgress();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // User cancelled, do nothing
                    dialog.dismiss();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void resetProgress() {
        // Use the ProgressManager to clear all saved data
        progressManager.resetAllProgress();

        // Use the SharedViewModel to notify other fragments (like ProgressFragment) to update their UI
        sharedViewModel.triggerProgressUpdate();

        // Show a confirmation message to the user
        Toast.makeText(getContext(), "All progress has been reset.", Toast.LENGTH_SHORT).show();
    }
}
