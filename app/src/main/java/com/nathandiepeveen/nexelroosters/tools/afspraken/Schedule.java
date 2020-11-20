package com.nathandiepeveen.nexelroosters.tools.afspraken;

public class Schedule {

    String classHour;
    String classInformation;
    String classChange;

    public Schedule(String classHour, String classInformation, String classChange)
    {
        this.classHour = classHour;
        this.classInformation = classInformation;
        this.classChange = classChange;
    }

    public String getClassHour() {
        return classHour;
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
