package com.nathandiepeveen.nexelroosters.tools.afspraken;

import java.util.Date;

public class Schedule {

    String classHour;
    String classInformation;
    String classChange;
    Date loadTime;

    public Schedule(String classHour, String classInformation, String classChange)
    {
        this.classHour = classHour;
        this.classInformation = classInformation;
        this.classChange = classChange;
        this.loadTime = new Date();
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
