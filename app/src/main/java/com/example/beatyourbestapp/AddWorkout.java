package com.example.beatyourbestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddWorkout extends AppCompatDialogFragment {
    private EditText editTextWorkoutName;
    private Button addRoutineBtn;
    private Button cancelBtn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the dialog with the custom style
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialogStyle);

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_workout, null);
        builder.setView(view);

        // Initialize views
        editTextWorkoutName = view.findViewById(R.id.editTextWorkoutName);
        Button addRoutineBtn = view.findViewById(R.id.buttonAddRoutine);
        Button cancelBtn = view.findViewById(R.id.buttonCancelAddRoutine);

        // Set button click listeners
        addRoutineBtn.setOnClickListener(v -> {
            String workoutName = editTextWorkoutName.getText().toString();
            // Process the workoutName if needed or call the listener to pass data back
            AddWorkoutListener listener = (AddWorkoutListener) getActivity();
            if (listener != null) {
                listener.onWorkoutAdded(workoutName);
            }
            dismiss(); // Dismiss the dialog after handling the button click
        });

        cancelBtn.setOnClickListener(v -> {
            // Handle cancel button click here if needed
            AddWorkoutListener listener = (AddWorkoutListener) getActivity();
            if (listener != null) {
                listener.onCancel();
            }
            dismiss(); // Dismiss the dialog after handling the button click
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            AddWorkoutListener listener = (AddWorkoutListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddWorkoutListener");
        }
    }

    public interface AddWorkoutListener {
        void applyTexts(String workoutName);
        void onCancel();

        void onWorkoutAdded(String workoutName);
    }
}

