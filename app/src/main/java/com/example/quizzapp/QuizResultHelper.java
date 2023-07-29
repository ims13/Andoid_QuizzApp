package com.example.quizzapp;

import android.content.Context;
import android.content.SharedPreferences;


public class QuizResultHelper {
    private static final String PREFERENCE_NAME = "QuizApp";
    private  static final String KEY_CORRECT_ANSWERS = "correct_answers";
    private  static  final String KEY_TOTAL_QUESTIONS = "total_questions";

    private final SharedPreferences sharedPreferences;

    public QuizResultHelper(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    //     saveQuizResult saves the result of a quiz attempt.
    public void  saveQuizResult(int correctAnswers, int totalQuestions){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_CORRECT_ANSWERS, correctAnswers);
        editor.putInt(KEY_TOTAL_QUESTIONS, totalQuestions);
        editor.apply();
    }

    public float getAverage() {
        // Retrieve the total and correct answers count from shared preferences
        int total = sharedPreferences.getInt(KEY_TOTAL_QUESTIONS, 0);
        int correct = sharedPreferences.getInt(KEY_CORRECT_ANSWERS, 0);

        // Check for divide by zero
        if (total == 0) {
            return 0;
        }
        // Calculate and return the average
        return (float) (((double) correct / total) * 100);

    }


    //  getCorrectAnswers  retrieve the total number of correct answers  and
    public int getCorrectAnswers(){
        return sharedPreferences.getInt(KEY_CORRECT_ANSWERS, 0);
    }

    // getTotalQuestions  retrieve the total questions attempted.
    public int getTotalQuestions(){
        return sharedPreferences.getInt(KEY_TOTAL_QUESTIONS, 0);
    }

    // resetResults resets all saved results.
    public void resetResults(){
        sharedPreferences.edit().clear().apply();
    }

}
