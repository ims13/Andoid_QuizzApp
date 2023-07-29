# Assignment_03_QuizzApp
# Quiz App

    Build an Android App with two languages and the use of File Storage

# Assignment Objectives:

    - Correctly use Android Fragments;
    - Build Constraint Layout;
    - Implement resource qualifier in Android;
    - Supporting
    - multiple languages
    - Efficiently
    - using File System and store some app data
    - internally
    - Save the activity state.

# Quiz Application

    •   This application is a quiz app with a build in Questions Bank, the quiz
        contains several True False questions.

    •   The user could save the quiz result number of correct answer / number
        for questions in the attempt after each attempt in the device's file system
        (Internal Private or External Private

    •   The user could get the number of correct questions in all attempts.

    •   The user could track his/her progress in the quiz using the progress bar.

    •   The user will know if his/her selection for any question is correct or not
        correct.

    •   The quiz must support another language of your choice.

# App layouts

    Main Activity Layout
        •   Fragment Container View for question fragment.
        •   Two buttons for true and false;
        •   Progress Bar

    Other layouts:
        •   Menu layout
        •   Fragment Layout which has one Text View;

# When the device rotated

    The activity state must be saved,
    so the user continue his/her quiz.

# When the quiz finished

    •   An alert dialog appears, showing the last attempt's results and asking the user
        to save the result or ignore them;
    •   If the user select to save, the number of correct questions and the total number 
        of questions will be stored in the File system (app specific data) internal or external
        storage overriding the existing information
    •   Otherwise, no storage will occur.
    •   The UI and all other quiz variables will be updated, preparing for another attempt.
    •   The quiz's questions and colors will be shuffled after each attempt.

# The app menu

    • Get The average: The user will see a report of his/her attempts
    • Select the number of questions.
    • Reset the saved results: all previous results in the file will be erased.

# Get the correct answers average

    The user can check the number of correct answers in all previous attempts' questions, 
    restored them from internal storage.

# Change the total number of questions

    • The user should be able to reduce the number of questions in the coming attempts using 
        dialog fragment.
    • The updated number of question must be less than the total number of questions 
        (originally created in the quiz)
    • Update the quiz UI to fit the new number of questions.

# Reset the saved results
    
    • When the user reset all saved results, the internal storage file will be erased
    • Checking the average again will show similar message.

# Support another language

    • If quiz app installed on a device with main language is not English, the quiz will run
      in the device's language version.
    • In this example, the quiz provide an Arabic version.(You could provide any language)

# Hints

    •   Create a model to represent one question (text, answer, color).
    •   Create a Question Bank model to manage the quiz questions andshuffle them
        ( the questions and colors after each attempt.
    •   Each question fragment need to receive the question's text and color fromMain Activity.
    •   Extract all strings from your code to strings.xml file, to support different
        language (questions, menu items, etc..).


                                       Good Luck# Assignment_03_QuizzApp
# Assignment_03_QuizzApp
