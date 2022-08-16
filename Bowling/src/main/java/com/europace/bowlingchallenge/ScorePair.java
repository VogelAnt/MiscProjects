package com.europace.bowlingchallenge;

public class ScorePair {
    private Integer firstScore = 0;
    private Integer secondScore = 0;
    private Boolean firstScoreSet = false;
    private boolean bothScoresSet = false;

    public Integer getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(Integer firstScore) {
        this.firstScore = firstScore;
        firstScoreSet = true;
    }

    public Integer getSecondScore() {
        return secondScore;
    }

    public void setSecondScore(Integer secondScore) {
        this.secondScore = secondScore;
        bothScoresSet = true;
    }

    public Boolean isFirstScoreSet() {
        return firstScoreSet;
    }

    public boolean areBothScoresSet() {
        return bothScoresSet;
    }

    public Integer getTotalScore(){
        return firstScore+secondScore;
    }
}
