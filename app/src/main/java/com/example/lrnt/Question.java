package com.example.lrnt;

public class Question {
    String answer;
    String c1;
    String c2;
    String c3;
    String c4;
    String question;

    public Question(String answer, String c1, String c2, String c3, String c4, String question) {
        this.answer = answer;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getC1() {
        return c1;
    }

    public String getC2() {
        return c2;
    }

    public String getC3() {
        return c3;
    }

    public String getC4() {
        return c4;
    }

    public String getQuestion() {
        return question;
    }
}
