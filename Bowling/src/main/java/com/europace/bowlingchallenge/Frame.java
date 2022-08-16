package com.europace.bowlingchallenge;

public abstract class Frame {
    protected ScorePair scorePair;
    protected static final Integer numBoniSpare = 1;
    protected static final Integer numBoniStrike = 2;
    private Integer numBoniAdded = 0;
    protected static final Integer totalPins = 10;
    protected ScoreState frameScoreState = ScoreState.NORMAL;
    protected Integer totalScore = 0;
    protected Boolean complete = false;
    protected Boolean previousFrameScoreAdded = false;
    private Boolean bonusComplete = false;

    Frame(){
        scorePair = new ScorePair();
    }

    public void addScore(Integer newScore) throws NullPointerException{
        if(isComplete()){return;}
        if(Boolean.TRUE.equals(getScorePair().isFirstScoreSet())){
            if(newScore+ getScorePair().getFirstScore()>=totalPins){
                setFrameScoreState(ScoreState.SPARE);
                getScorePair().setSecondScore(totalPins - getScorePair().getFirstScore());
                setComplete(true);
            }else{
                getScorePair().setSecondScore(newScore);
                setComplete(true);
            }
        }else if(newScore.equals(totalPins)) {
            setFrameScoreState(ScoreState.STRIKE);
            getScorePair().setFirstScore(totalPins);
            getScorePair().setSecondScore(0);
            setComplete(true);
        } else{
            setFrameScoreState(ScoreState.NORMAL);
            getScorePair().setFirstScore(newScore);
        }if(Boolean.TRUE.equals(areBothScoresSet())){
            setTotalScore(getScorePair().getTotalScore());
             setComplete(true);
        }
    }

    public Boolean isFirstScoreSet(){
        return getScorePair().isFirstScoreSet();
    }

    public Boolean areBothScoresSet(){return getScorePair().areBothScoresSet();}

    public void addBonus(Integer bonus){
        if(frameScoreState.equals(ScoreState.SPARE) && getNumBoniAdded() < numBoniSpare){
            setTotalScore(getTotalScore() + bonus);
            ++numBoniAdded;

        }else if(frameScoreState.equals(ScoreState.STRIKE) && getNumBoniAdded() < numBoniStrike){
            setTotalScore(getTotalScore() + bonus);
            ++numBoniAdded;
        }
    }

    public String getFrameScores() {
        if(getFrameScoreState().equals(ScoreState.STRIKE)){
            return "X|";
        }else if(getFrameScoreState().equals(ScoreState.SPARE)){
            return getScorePair().getFirstScore() + "|/";
        }
        if(!getScorePair().areBothScoresSet()){
            return getScorePair().getFirstScore()+"|";
        }return getScorePair().getFirstScore()+"|"+ getScorePair().getSecondScore();
    }

    public abstract Integer getTotalScore();

    public Integer getFirstScore(){
        return getScorePair().getFirstScore();
    }

    public Integer getSecondScore(){
        return getScorePair().getSecondScore();
    }

    public ScoreState getScoreState(){
        return getFrameScoreState();
    }

    public ScorePair getScorePair() {
        return scorePair;
    }

    public void sumPreviousScore(Integer prevScores){
        setTotalScore(getTotalScore() + prevScores);
    }

    public void setScorePair(ScorePair scorePair) {
        this.scorePair = scorePair;
    }

    public ScoreState getFrameScoreState() {
        return frameScoreState;
    }

    public void setFrameScoreState(ScoreState frameScoreState) {this.frameScoreState = frameScoreState;}

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Boolean isComplete() {return complete;}

    public void setComplete(Boolean complete) {this.complete = complete;}

    public boolean isPreviousFrameScoreAdded() {
        return previousFrameScoreAdded;
    }

    public void setPreviousFrameScoreAdded(boolean previousFrameScoreAdded) {
        this.previousFrameScoreAdded = previousFrameScoreAdded;
    }

    public Integer getNumBoniAdded() {
        return numBoniAdded;
    }

    public Boolean isBonusComplete() {
        return bonusComplete;
    }

    public abstract Integer getTotalScoreAfterStrike();

    public void setBonusComplete(Boolean bonusComplete) {
        this.bonusComplete = bonusComplete;
    }
}
