package com.github.vaseghifard.weatherapplication.models;

public class NextDaysItemsModel {
    private String nameOfDays;
    private String tempOfdays;
    private String imageOfDays;
    private int codeImageOfDays;

    public NextDaysItemsModel(String nameOfDays, String tempOfdays, int codeImageOfDays) {
        this.nameOfDays = nameOfDays;
        this.tempOfdays = tempOfdays;
        this.codeImageOfDays = codeImageOfDays;
    }

    public NextDaysItemsModel(String nameOfDays, String tempOfdays, String imageOfDays) {
        this.nameOfDays = nameOfDays;
        this.tempOfdays = tempOfdays;
        this.imageOfDays = imageOfDays;
    }

    public NextDaysItemsModel() {
    }

    public String getNameOfDays() {
        return nameOfDays;
    }

    public void setNameOfDays(String nameOfDays) {
        this.nameOfDays = nameOfDays;
    }

    public String getTempOfdays() {
        return tempOfdays;
    }

    public void setTempOfdays(String tempOfdays) {
        this.tempOfdays = tempOfdays;
    }

    public String getImageOfDays() {
        return imageOfDays;
    }

    public void setImageOfDays(String imageOfDays) {
        this.imageOfDays = imageOfDays;
    }

    public int getCodeImageOfDays() {
        return codeImageOfDays;
    }

    public void setCodeImageOfDays(int codeImageOfDays) {
        this.codeImageOfDays = codeImageOfDays;
    }
}
