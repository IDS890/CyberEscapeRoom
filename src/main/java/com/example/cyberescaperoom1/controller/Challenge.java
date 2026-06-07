package com.example.cyberescaperoom1.controller;

import java.util.List;

public class Challenge {
    private int id;
    private String title;
    private String missionBriefing;
    private String evidenceHtml;
    private List<InvestigationTask> tasks;
    private InvestigationTask finalDecision;
    private String debriefing;

    public Challenge(int id, String title, String missionBriefing, String evidenceHtml,
                     List<InvestigationTask> tasks, InvestigationTask finalDecision, String debriefing) {
        this.id = id;
        this.title = title;
        this.missionBriefing = missionBriefing;
        this.evidenceHtml = evidenceHtml;
        this.tasks = tasks;
        this.finalDecision = finalDecision;
        this.debriefing = debriefing;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getMissionBriefing() { return missionBriefing; }
    public String getEvidenceHtml() { return evidenceHtml; }
    public List<InvestigationTask> getTasks() { return tasks; }
    public InvestigationTask getFinalDecision() { return finalDecision; }
    public String getDebriefing() { return debriefing; }
}