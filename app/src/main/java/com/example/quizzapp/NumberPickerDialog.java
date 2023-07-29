package com.example.quizzapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

public class NumberPickerDialog extends DialogFragment {

    public interface OnNumberSetListener {
        void onNumberSet(int number);
    }
    private OnNumberSetListener mListener;

    public void setOnNumberSetListener(OnNumberSetListener listener) {
        this.mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(50); // let's say a maximum of 50 questions

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose the number of questions");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onNumberSet(numberPicker.getValue());
                }
            }
        });

        builder.setNegativeButton("CANCEL", null);
        builder.setView(numberPicker);
        return builder.create();
    }
}
