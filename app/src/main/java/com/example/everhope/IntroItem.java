package com.example.everhope;

public class IntroItem {
    String guileLine, title;
    int imageID;

    public IntroItem(String guileLine, String title, int imageID){
        this.guileLine = guileLine;
        this.title = title;
        this.imageID = imageID;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setGuileLine(String guileLine) {
        this.guileLine = guileLine;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }

    public String getTitle() {
        return title;
    }

    public String getGuileLine() {
        return guileLine;
    }
}
