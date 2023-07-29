package com.example.quizzapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

//import java.util.List;
//import com.example.quizzapp.Question;  // Assuming 'Question' is the class that represents a single question.



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    // The color field is now a color resource ID
    private int color;
    // Field can be converted to a local variable
    private String question;
    private boolean answer;

    private static final String ARG_QUESTION = "arg_question";
    private static final String ARG_ANSWER = "arg_answer";

    private static final String ARG_COLOR = "arg_color";

    private ProgressBar progressBar;
    private int totalQuestions;

    private int currentQuestionNumber = 0;


    private OnAnswerSubmittedListener answerSubmittedListener;

    private static final String ARG_TOTAL_QUESTIONS = "arg_total_questions";


    private static final String ARG_CURRENT_QUESTION_NUMBER = "arg_current_question_number";



    // Static factory method to create a new instance of the fragment
    public static QuestionFragment newInstance(String question, boolean answer, int colorResId,  int totalQuestions) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION, question);
        args.putBoolean(ARG_ANSWER, answer);
        args.putInt(ARG_COLOR, colorResId);
        args.putInt(ARG_TOTAL_QUESTIONS, totalQuestions);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(ARG_QUESTION);
            answer = getArguments().getBoolean(ARG_ANSWER, false);
            color = getArguments().getInt(ARG_COLOR, R.color.default_color);
            totalQuestions = getArguments().getInt(ARG_TOTAL_QUESTIONS);
        }

        if(savedInstanceState != null) {
            currentQuestionNumber = savedInstanceState.getInt(ARG_CURRENT_QUESTION_NUMBER, 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionTextView = view.findViewById(R.id.question_text_view);
        Button trueButton = view.findViewById(R.id.true_button);
        Button falseButton = view.findViewById(R.id.false_button);

        // Initialize the ProgressBar
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.progress_default)));

        // Set the maximum progress to the total number of questions
        progressBar.setMax(totalQuestions);

        // a method or a way to get the total number of questions in the quiz
        // totalQuestions = getTotalQuizQuestions();

        // Setting the question to the TextView
        questionTextView.setText(question);

        // Get the actual color value
        @ColorInt int colorValue;
        try {
            Context context = getContext();
            if (context != null) {
                colorValue = ContextCompat.getColor(context, color);
            } else {
                throw new Resources.NotFoundException("Context is null");
            }
        } catch (Resources.NotFoundException e) {
            // Color resource does not exist, or context is null, use a default color
            Context context = getContext();
            if (context != null) {
                colorValue = context.getResources().getColor(R.color.default_color, null);
            } else {
                // Handle this unlikely case appropriately for your app; below is just an example
                colorValue = Color.BLACK;
            }
        }

        // Set the color of the ProgressBar
        progressBar.setProgressTintList(ColorStateList.valueOf(colorValue));

        // Set the background color of the question TextView
        questionTextView.setBackgroundColor(colorValue);

        trueButton.setOnClickListener(v -> {
            // Check if the answer is correct
            boolean isAnswerCorrect= checkAnswer(true);
            // Provide a feedback whether the answer was true or false
            provideFeedback(isAnswerCorrect, true);

            // handle true button click
           updateProgressBar();  // update the progress bar regardless of the answer correctness

        });

        falseButton.setOnClickListener(v -> {
            // Check if the answer is correct
            boolean isAnswerCorrect = checkAnswer(false);
            // Provide feedback based on whether the answer is correct or not
            provideFeedback(isAnswerCorrect, false);

            // Now that progressBar is initialized, call updateProgressBar()
             updateProgressBar();
        });

        // Call updateProgressBar() here to set the initial progress
        updateProgressBar();


        return view;
    }


    // TODO: Implement these methods.
    private boolean checkAnswer(boolean userAnswer) {
        return userAnswer == answer;
    }

    // method checks if the listener is null and then calls the onAnswerSubmitted() method
    // to prevent NullPointerExceptions.
    private void provideFeedback(boolean isAnswerCorrect, boolean userAnswer) {
        if (answerSubmittedListener == null) {
            return;

        }

        answerSubmittedListener.onAnswerSubmitted(isAnswerCorrect, userAnswer);
    }

    public void updateTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
        // Update the progress bar maximum value
        progressBar.setMax(totalQuestions);
    }

    public interface OnAnswerSubmittedListener {
        void onAnswerSubmitted(boolean isCorrect, boolean userAnswer);
    }



    public void setOnAnswerSubmittedListener(OnAnswerSubmittedListener listener) {
        this.answerSubmittedListener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAnswerSubmittedListener) {
            answerSubmittedListener = (OnAnswerSubmittedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnAnswerSubmittedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        answerSubmittedListener = null;
    }

    private void updateProgressBar() {
        if (progressBar != null) {
            currentQuestionNumber++;
            if (currentQuestionNumber == totalQuestions) {
                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.progress_finished)));
            }
            progressBar.setProgress(currentQuestionNumber);
        } else {
            Log.e("QuestionFragment", "ProgressBar is null. Check if it's initialized properly.");
        }
    }

//    public void updateProgressBar(int currentQuestionNumber, int totalQuestions) {
//        if (progressBar != null) {
//            int progress = (int) (((float) currentQuestionNumber / totalQuestions) * 100);
//            progressBar.setProgress(progress);
//
//            // If all questions have been answered, change the progress bar color
//            if (progress == 100) {
//                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.progress_finished)));
//            }
//        } else {
//            Log.e("QuestionFragment", "ProgressBar is null. Check if it's initialized properly.");
//        }
//    }

    public void updateProgressBar(int answeredQuestions, int totalQuestions) {
        if (progressBar != null) {
            progressBar.setProgress(answeredQuestions);
            progressBar.setMax(totalQuestions);
        }
    }



    private int getTotalQuizQuestions() {
        // this method to return the total number of questions
        return totalQuestions;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_CURRENT_QUESTION_NUMBER, currentQuestionNumber);
    }


}