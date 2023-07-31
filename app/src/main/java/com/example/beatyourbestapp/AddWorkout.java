package com.example.beatyourbestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddWorkout extends AppCompatDialogFragment {
    private EditText editTextWorkoutName;
    private Button addRoutineBtn;
    private Button cancelBtn;
    private AddWorkoutListener listener;

    // Method to set the listener
    public void setListener(AddWorkoutListener listener) {
        this.listener = listener;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Create the dialog with the custom style
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialogStyle);

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_workout, null);
        builder.setView(view);

        Spinner spinner = view.findViewById(R.id.spinnerDayOfWeek);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Initialize views
        editTextWorkoutName = view.findViewById(R.id.editTextWorkoutName);
        addRoutineBtn = view.findViewById(R.id.buttonAddRoutine);
        cancelBtn = view.findViewById(R.id.buttonCancelAddRoutine);

        // Set button click listeners
        addRoutineBtn.setOnClickListener(v -> {
            String workoutName = editTextWorkoutName.getText().toString();
            String workoutDay = spinner.getSelectedItem().toString();
            // Process the workoutName if needed or call the listener to pass data back
            if (listener != null) {
                listener.onWorkoutAdded(workoutName, workoutDay);
            }
            dismiss(); // Dismiss the dialog after handling the button click
        });

        cancelBtn.setOnClickListener(v -> {
            // Handle cancel button click here if needed
            if (listener != null) {
                listener.onCancel();
            }
            dismiss(); // Dismiss the dialog after handling the button click
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }



    public interface AddWorkoutListener {

        void onCancel();

        void onWorkoutAdded(String workoutName, String workoutDay);
    }
    //    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            AddWorkoutListener listener = (AddWorkoutListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement AddWorkoutListener");
//        }
//    }
}

