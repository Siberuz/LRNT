package com.example.lrnt;

public class ObjectCourse {
    private String title;
    private String desc;

    public ObjectCourse(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}