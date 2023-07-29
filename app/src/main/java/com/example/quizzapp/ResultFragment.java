package com.example.quizzapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class ResultFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUIZ_RESULT = "quizResult";
    private String quizResult;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance(String quizResult) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUIZ_RESULT, quizResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // To make sure that quizResult will not be null and "No result available" will
            // be used if ARG_QUIZ_RESULT is not in the arguments.
            quizResult = getArguments().getString(ARG_QUIZ_RESULT, "No result available");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // Update the result TextView
        TextView resultTextView = view.findViewById(R.id.result_text_view);
        resultTextView.setText(quizResult);

        return view;
    }
}
