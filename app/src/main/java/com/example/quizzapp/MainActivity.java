package com.example.quizzapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.widget.Toast;


import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuItem;



import java.util.Arrays;
import java.util.Collections;

import java.util.List;




public class MainActivity extends AppCompatActivity implements
        QuestionFragment.OnAnswerSubmittedListener,
        NumberPickerDialog.OnNumberSetListener {

    //public class MainActivity extends AppCompatActivity {
    // KEY_INDEX is a constant used as the key for the value that will be saved in the Bundle.
    private static final String KEY_INDEX = "index";

    // currentIndex is the variable that we want to save and restore.
    private int currentIndex = 0;

    // Declare the field but not initialize it yet.

    private boolean[] userAnswers;

    // Declare questionList here
    private Question[] questionList;

    //initialize quizResultHelper
    private QuizResultHelper quizResultHelper;

    private int correctAnswers = 0;
    private int totalQuestions = 0;

    private List<Integer> colorList;

    private ImageView emojiImageView;

    private int answeredQuestions = 0;

    //private boolean isFirstCreation = true;

    private QuestionFragment questionFragment;

    private int currentQuestionNumber = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate QuizResultHelper in Create()
        quizResultHelper = new QuizResultHelper(this);

        emojiImageView = findViewById(R.id.emoji_image_view);

        // Check if it's the first creation or not

        // Show the NumberPickerDialog only if it's the first creation
        if (savedInstanceState == null) {
            NumberPickerDialog numberPickerDialog = new NumberPickerDialog();
            numberPickerDialog.setOnNumberSetListener(this);
            numberPickerDialog.show(getSupportFragmentManager(), "numberPicker");
        }

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("currentIndex");
            totalQuestions = savedInstanceState.getInt("totalQuestions");
            answeredQuestions = savedInstanceState.getInt("answeredQuestions");
            correctAnswers = savedInstanceState.getInt("correctAnswers");
        }
       // new NumberPickerDialog().show(getSupportFragmentManager(), "numberPicker");

        // Initialize questionList
        questionList = new Question[]{

                // Islamic questions:
                new Question("Is the Quran the holy book of Islam?", true),
                new Question("Did Prophet Muhammad (peace be upon him) receive revelations from Angel Gabriel?", true),
                new Question("Are there seven pillars in Islam?", false),
                new Question("Is Zakat one of the five pillars of Islam?", true),
                new Question("Is Ramadan the month of fasting in Islam?", true),
                new Question("Is the Hijri calendar used in Islam based on the lunar cycle?", true),
                new Question("Is Mecca considered the birthplace of Prophet Muhammad (peace be upon him)?", true),
                new Question("Does Islam believe in reincarnation after death?", false),
                new Question("Is Friday the holy day of the week in Islam?", true),
                new Question("Are Muslims required to pray only three times a day?", false),

                // ... previous questions ...
                new Question("Is the capital of Australia Sydney?", false),
                new Question("Is Paris the capital of France?", true),
                new Question("Is the capital of Canada Toronto?", false),
                new Question("Is Moscow the capital of Russia?", true),
                new Question("Is Beijing the capital of Japan?", false),
                new Question("Is London the capital of the United Kingdom?", true),
                new Question("Is New Delhi the capital of Pakistan?", false),
                new Question("Is Ankara the capital of Turkey?", true),
                new Question("Is Washington, D.C. the capital of the United States?", true),
                new Question("Is Pretoria the capital of South Africa?", true),
                // More questions...
        };

        validateQuestionListSize();

        // this color definitions as integer resource IDs.
        colorList = Arrays.asList(
                R.color.material_red_500,
                R.color.material_pink_500,
                R.color.material_purple_500,
                R.color.material_deep_purple_500,
                R.color.material_indigo_500,
                R.color.material_blue_500,
                R.color.material_light_blue_500,
                R.color.material_cyan_500,
                R.color.material_teal_500,
                R.color.material_green_500
        );

        // Now initialize userAnswers
        userAnswers = new boolean[questionList.length];

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        //QuestionFragment questionFragment = QuestionFragment.newInstance(questionList[currentIndex].getQuestionText(), questionList[currentIndex].isAnswer(), getResources().getColor(R.color.default_color));

        QuestionFragment questionFragment = QuestionFragment.newInstance(
                questionList[currentIndex].getQuestionText(),
                questionList[currentIndex].isAnswer(),
                colorList.get(currentIndex % colorList.size()),
                totalQuestions
        );

        // Set MainActivity as the OnAnswerSubmittedListener
        questionFragment.setOnAnswerSubmittedListener(this);

        // Assign the questionFragment instance
        this.questionFragment = questionFragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, questionFragment)
                .commit();

    }

    @Override
    public void onNumberSet(int number) {
        // Use the selected number here to set the number of questions
       // Log.d("MainActivity", "Number of questions: " + number);
        adjustQuestionArray(number);
        showQuestion(questionList[currentIndex]);
    }


    public void validateQuestionListSize() {
        int size = questionList.length;
        Log.d("QuizApp", "Size of questionList is: " + size);
    }


    public void adjustQuestionArray(int numQuestions) {
        // logic for adjusting the questionList and userAnswers array size
        // need to ensure we're not trying to request more questions than exist
        int totalQuestionsAvailable = questionList.length;
        numQuestions = Math.min(numQuestions, totalQuestionsAvailable);
        questionList = Arrays.copyOf(questionList, numQuestions);
        userAnswers = new boolean[questionList.length]; // Adjust the userAnswers array as well

        // Reset counters and currentIndex
        correctAnswers = 0;
        totalQuestions = questionList.length; // <-- update this line
        currentIndex = 0;
        answeredQuestions = 0; // Reset the count of answered questions to zero

        // Update the total number of questions in the progress bar
        updateTotalQuestions(totalQuestions);

        // Display the first question
        showQuestion(questionList[currentIndex]);
    }


    //  when the device is rotated, the value of currentIndex is saved and restored
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putInt(KEY_INDEX, currentIndex);
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("currentIndex", currentIndex);
        outState.putInt("totalQuestions", totalQuestions);
        outState.putInt("answeredQuestions", answeredQuestions);
        outState.putInt("correctAnswers", correctAnswers);
    }


    //    The @NonNull annotation is a form of documentation and can be used by various tools to catch
    //    potential errors in your code (like passing a null value to a method or variable that should
    //            never be null).

    // The app menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // When a user clicks on a menu item,
    // the onOptionsItemSelected method is called with the ID of the clicked item.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_get_average) {
            // fetch average from QuizResultHelper
            float average = quizResultHelper.getAverage();
            // display average in a dialog
            new AlertDialog.Builder(this)
                    .setTitle("Average Score")
                    .setMessage("Your average score is: " + average)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return true;
        } else if (id == R.id.action_select_questions_fixed) {
            // display dialog to select number of questions
            String[] options = {"5", "10", "15", "20"};
            new AlertDialog.Builder(this)
                    .setTitle("Select Number of Questions")
                    .setItems(options, (dialog, which) -> {
                        // adjust your question array based on user selection
                        adjustQuestionArray(Integer.parseInt(options[which]));
                    })
                    .show();
            return true;
        } else if (id == R.id.action_select_questions_flexible) {
            // display dialog to select number of questions
            new NumberPickerDialog().show(getSupportFragmentManager(), "numberPicker");
            return true;
        } else if (id == R.id.action_reset_results) {
            // delete all saved results
            quizResultHelper.resetResults();
            Toast.makeText(this, "Results reset!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Result");
        builder.setMessage("You got " + correctAnswers + " out of " + answeredQuestions + " questions correct.");
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Save the results here
            quizResultHelper.saveQuizResult(correctAnswers, totalQuestions);
        });
        builder.setNegativeButton("Ignore", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(dialogInterface -> {
            // Reset the quiz The resetQuiz() method resets the correctAnswers, totalQuestions,
            // and currentIndex variables to their initial states.
            resetQuiz();
        });
        dialog.show();
    }

    public void resetQuiz() {
        // Reset variables
        correctAnswers = 0;
        totalQuestions = 0;
        currentIndex = 0;
        answeredQuestions = 0; // Reset the count of answered questions to zero

        // Shuffle questions
        Collections.shuffle(Arrays.asList(questionList));

        // Reset UI
        Question currentQuestion = questionList[currentIndex];
        int colorRes = ContextCompat.getColor(this, colorList.get(currentIndex % colorList.size()));
        QuestionFragment questionFragment = QuestionFragment.newInstance(
                currentQuestion.getQuestionText(),
                currentQuestion.isAnswer(),
                colorRes,
                totalQuestions
        );

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, questionFragment)
                .commit();

        questionFragment.updateProgressBar(answeredQuestions, totalQuestions);

        showNextQuestion();
    }

    // and currentIndex is the index of the current question.
    public void showNextQuestion() {
        if (currentIndex >= questionList.length - 1) {
            // We've reached the end of the quiz.
            showResultDialog();
        } else {
            // Show the next question.
            currentIndex++;

            // Update TextView color
            int colorId = colorList.get(currentIndex % colorList.size());
            int colorRes = ContextCompat.getColor(this, colorId);

            Question currentQuestion = questionList[currentIndex];
            QuestionFragment newQuestionFragment = QuestionFragment.newInstance(
                    currentQuestion.getQuestionText(),
                    currentQuestion.isAnswer(),
                    colorRes,
                    totalQuestions
            );

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, newQuestionFragment)
                    .commit();

            // Update the reference to the current QuestionFragment
            questionFragment = newQuestionFragment;

            // Update the progress bar when showing the next question
            questionFragment.updateProgressBar(answeredQuestions, totalQuestions);

            showQuestion(questionList[currentIndex]);
        }
    }

    private void showQuestion(Question question) {
        // Create new QuestionFragment
        QuestionFragment questionFragment = QuestionFragment.newInstance(
                question.getQuestionText(),
                question.isAnswer(),
                colorList.get(currentIndex % colorList.size()),
                totalQuestions
        );

        // Set MainActivity as the OnAnswerSubmittedListener
        questionFragment.setOnAnswerSubmittedListener(this);

        // Perform the fragment transaction (replace existing fragment, etc)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, questionFragment).commit();

        // Update the reference to the current QuestionFragment
        this.questionFragment = questionFragment;

        // Update the progress bar when showing the next question
        questionFragment.updateProgressBar(answeredQuestions, totalQuestions);

    }

    @Override
    public void onAnswerSubmitted(boolean isCorrect, boolean userAnswer) {
        // resize the emoji ImageView
        int emojiSize = 400; // size in pixels, change to desired size
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(emojiSize, emojiSize);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        emojiImageView.setLayoutParams(layoutParams);

        // Change the emoji and make it visible
        int emojiRes = isCorrect ? R.drawable.thumbs_up : R.drawable.thumbs_down;
        emojiImageView.setImageResource(emojiRes);
        emojiImageView.setVisibility(View.VISIBLE);

        // If the answer is correct, increment the count of correct answers and show a toast
        if (isCorrect) {
            Toast.makeText(this, "Correct answer!", Toast.LENGTH_SHORT).show();
            correctAnswers++;
        } else {
            // If the answer is incorrect, show a different toast
            Toast.makeText(this, "Incorrect answer.", Toast.LENGTH_SHORT).show();
        }

        // Increase the count of total questions answered
        // totalQuestions++;
        // Increase the count of answered questions
        answeredQuestions++;

        // Update the progress bar when the user answers a question
        // This will increase the progress bar as the user goes to the next question
        if (questionFragment != null) {
            questionFragment.updateProgressBar(answeredQuestions, totalQuestions);
        }


        // Delay the showing of the next question to allow the emoji to be seen
        emojiImageView.postDelayed(() -> {
            emojiImageView.setVisibility(View.INVISIBLE);  // Hide the emoji before moving to the next question
            showNextQuestion();
        }, 2000);  // Delay of 2 seconds
    }

    public void updateTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
        // Is Passed to the QuestionFragment as well
        // QuestionFragment questionFragment = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (questionFragment != null) {
            questionFragment.updateTotalQuestions(totalQuestions);
            // Update the progress bar in the fragment based on the number of answered questions
            questionFragment.updateProgressBar(answeredQuestions, totalQuestions);
        }
    }
}



