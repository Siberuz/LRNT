package com.example.lrnt;

public class Leaderboard_Score {
    String name;
    int score;
    int pos;

    public Leaderboard_Score(String name, int score, int pos) {
        this.name = name;
        this.score = score;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
