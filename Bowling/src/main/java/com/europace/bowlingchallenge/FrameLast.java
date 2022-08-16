package com.europace.bowlingchallenge;

public class FrameLast extends Frame{
    private ScorePair extraScore;
    private Integer prevScore = 0;

    FrameLast(){
        super();
        extraScore = new ScorePair();
        setBonusComplete(true);
    }

    @Override
    public void addScore(Integer newScore){
        if(scorePair.areBothScoresSet() && !frameScoreState.equals(ScoreState.NORMAL)){
            computeExtraScorePair(newScore);
            totalScore += extraScore.getTotalScore();
            }else if(!scorePair.areBothScoresSet()){
            computeNormalScorePair(newScore);
            totalScore += newScore;
        }
    }

    public void computeExtraScorePair(Integer newScore){
        if (frameScoreState.equals(ScoreState.SPARE)){
            extraScore.setFirstScore(newScore);
            setComplete(true);
        }else if(frameScoreState.equals(ScoreState.STRIKE)) {
            if(Boolean.TRUE.equals(extraScore.isFirstScoreSet())){
                extraScore.setSecondScore(newScore);
                setComplete(true);
            }else{
                extraScore.setFirstScore(newScore);
            }
        }
    }

    public void computeNormalScorePair(Integer newScore){
        if(Boolean.TRUE.equals(getScorePair().isFirstScoreSet())){
            if(newScore + getScorePair().getFirstScore()>=totalPins){
                setFrameScoreState(ScoreState.SPARE);
                getScorePair().setSecondScore(totalPins - getScorePair().getFirstScore());
            }else{
                getScorePair().setSecondScore(newScore);
                setComplete(true);
            }}else if(newScore.equals(totalPins)) {
            setFrameScoreState(ScoreState.STRIKE);
            getScorePair().setFirstScore(totalPins);
            getScorePair().setSecondScore(0);
            setComplete(false);
        } else{
            setFrameScoreState(ScoreState.NORMAL);
            getScorePair().setFirstScore(newScore);
        }if(Boolean.TRUE.equals(areBothScoresSet()) && frameScoreState.equals(ScoreState.NORMAL)){ // && !(frameScoreState.equals(ScoreState.SPARE) || frameScoreState.equals(ScoreState.STRIKE))
            setTotalScore(getScorePair().getTotalScore());
            setComplete(true);
        }
    }

    @Override
    public String getFrameScores(){
        switch(frameScoreState){
            case STRIKE -> {return "X|"+extraScore.getFirstScore()+"|"+extraScore.getSecondScore();}
            case SPARE -> {return scorePair.getFirstScore()+"|/|"+ extraScore.getFirstScore();}
            default -> {return scorePair.getFirstScore()+"|"+scorePair.getSecondScore(); }
        }
    }

    @Override
    public Integer getTotalScore(){ // for spare 9 is not added to the total score
        return scorePair.getTotalScore() + extraScore.getTotalScore() + prevScore;
    }

    @Override
    public Integer getTotalScoreAfterStrike(){return scorePair.getTotalScore() + extraScore.getFirstScore();}

    public ScorePair getExtraScore() {
        return extraScore;
    }

    @Override
    public void sumPreviousScore(Integer prevScore){
        this.prevScore = prevScore;
    }
}
