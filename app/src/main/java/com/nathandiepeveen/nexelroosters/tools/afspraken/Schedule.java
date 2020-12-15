package com.nathandiepeveen.nexelroosters.tools.afspraken;

import java.util.Date;

public class Schedule {

    String classHour;
    String classInformation;
    String classChange;
    Boolean cancelled;
    Date loadTime;

    public Schedule(String classHour, String classInformation, String classChange, Boolean state)
    {
        this.classHour = classHour;
        this.classInformation = classInformation;
        this.classChange = classChange;
        this.loadTime = new Date();
        this.cancelled = state;
    }

    public String getClassHour() {
        return classHour;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setClassHour(String classHour) {
        this.classHour = classHour;
    }

    public String getClassInformation() {
        return classInformation;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setClassInformation(String classInformation) {
        this.classInformation = classInformation;
    }

    public String getClassChange() {
        return classChange;
    }

    public void setClassChange(String classChange) {
        this.classChange = classChange;
    }
}
