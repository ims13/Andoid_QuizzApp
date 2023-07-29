package com.example.quizzapp;

public class Question {
    private final String questionText;
    private final boolean answer;

    public Question(String questionText, boolean answer) {
        this.questionText = questionText;
        this.answer = answer;
    }

    // getters
    public String getQuestionText() {
        return questionText;
    }

    public boolean isAnswer() {
        return answer;
    }
}
