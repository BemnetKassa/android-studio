package com.example.basicsofict.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.basicsofict.R;
import com.example.basicsofict.utils.ProgressManager;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Settings logic will be added later

        return view;
    }
    // Add this to your SettingsFragment
    private void setupResetProgress() {
        Button btnReset = getView().findViewById(R.id.btn_reset_progress);
        if (btnReset != null) {
            btnReset.setOnClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Reset Progress")
                        .setMessage("Are you sure you want to reset all progress? This cannot be undone.")
                        .setPositiveButton("Reset", (dialog, which) -> {
                            ProgressManager progressManager = new ProgressManager(requireContext());
                            progressManager.resetAllProgress();
                            Toast.makeText(requireContext(), "Progress reset successfully", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        }
    }
}