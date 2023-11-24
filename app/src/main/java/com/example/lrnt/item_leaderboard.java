package com.example.lrnt;

public class item_leaderboard {

    String participant;
    int rank;
    double point;

    public item_leaderboard(String participant, int rank, Double point) {
        this.participant = participant;
        this.rank = rank;
        this.point = point;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
}
