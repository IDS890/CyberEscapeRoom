package com.example.cyberescaperoom1.controller;

import java.util.Map;

public class InvestigationTask {
    private String id;
    private String question;
    private Map<String, String> options;
    private String correctAnswer;

    public InvestigationTask(String id, String question, Map<String, String> options, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getId() { return id; }
    public String getQuestion() { return question; }
    public Map<String, String> getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
}