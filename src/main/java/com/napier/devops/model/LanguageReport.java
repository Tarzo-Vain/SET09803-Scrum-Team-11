package com.napier.devops.model;

//package com.napier.devops.model;

public class LanguageReport {
    private String language;
    private long speakers;
    private double percentOfWorld;

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public long getSpeakers() { return speakers; }
    public void setSpeakers(long speakers) { this.speakers = speakers; }

    public double getPercentOfWorld() { return percentOfWorld; }
    public void setPercentOfWorld(double percentOfWorld) { this.percentOfWorld = percentOfWorld; }
}
