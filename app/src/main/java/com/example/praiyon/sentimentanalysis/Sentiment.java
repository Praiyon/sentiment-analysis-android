package com.example.praiyon.sentimentanalysis;

/**
 * Created by praiyon on 08/01/18.
 */

public class Sentiment {

    private String tweet;
    private Double polarity;
    private Double subjectivity;

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Double getPolarity() {
        return polarity;
    }

    public void setPolarity(Double polarity) {
        this.polarity = polarity;
    }

    public Double getSubjectivity() {
        return subjectivity;
    }

    public void setSubjectivity(Double subjectivity) {
        this.subjectivity = subjectivity;
    }
}
