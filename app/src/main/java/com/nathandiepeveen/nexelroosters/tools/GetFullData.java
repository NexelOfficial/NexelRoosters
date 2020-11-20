package com.nathandiepeveen.nexelroosters.tools;

import com.nathandiepeveen.nexelroosters.MainActivity;

public class GetFullData {

    public String getSubjectFull(String subject)
    {
        if (subject.equalsIgnoreCase("netl") || subject.equalsIgnoreCase("ne"))
            return "Nederlands";
        if (subject.equalsIgnoreCase("entl") || subject.equalsIgnoreCase("en"))
            return "Engels";
        if (subject.equalsIgnoreCase("dutl") || subject.equalsIgnoreCase("du"))
            return "Duits";
        if (subject.equalsIgnoreCase("fatl") || subject.equalsIgnoreCase("fa"))
            return "Frans";
        if (subject.equalsIgnoreCase("econ") || subject.equalsIgnoreCase("eco") || subject.equalsIgnoreCase("ec"))
            return "Economie";
        if (subject.equalsIgnoreCase("ckv"))
            return "CKV";
        if (subject.equalsIgnoreCase("me"))
            return "Mentor Uur";
        if (subject.equalsIgnoreCase("biol") || subject.equalsIgnoreCase("bio") || subject.equalsIgnoreCase("bi"))
            return "Biologie";
        if (subject.equalsIgnoreCase("go"))
            return "Godsdienst";
        if (subject.equalsIgnoreCase("lo") || subject.equalsIgnoreCase("gym"))
            return "Gym";
        if (subject.equalsIgnoreCase("schk") || subject.equalsIgnoreCase("sk"))
            return "Scheikunde";
        if (subject.equalsIgnoreCase("nat") || subject.equalsIgnoreCase("na"))
            return "Natuurkunde";
        if (subject.equalsIgnoreCase("mu") || subject.equalsIgnoreCase("muzk"))
            return "Muziek";
        if (subject.equalsIgnoreCase("gs") || subject.equalsIgnoreCase("ges"))
            return "Geschiedenis";
        if (subject.equalsIgnoreCase("in") || subject.equalsIgnoreCase("inf"))
            return "Informatica";
        if (subject.equalsIgnoreCase("bec") || subject.equalsIgnoreCase("bedr"))
            return "Bedrijfseconomie";
        if (subject.equalsIgnoreCase("ak"))
            return "Aardrijkskunde";
        if (subject.contains("wis"))
            return "Wiskunde";
        if (subject.contains("beta"))
            return "Beta Uur";
        return subject;
    }

    public String getFullType(String type)
    {
        if (type.equalsIgnoreCase("lesson"))
            return "Les";
        if (type.equalsIgnoreCase("exam"))
            return "Examen";
        if (type.equalsIgnoreCase("activity"))
            return "Activiteit";
        if (type.equalsIgnoreCase("choice"))
            return "Keuze";
        if (type.equalsIgnoreCase("talk"))
            return "Gesprek";
        if (type.equalsIgnoreCase("other"))
            return "Overige";

        return "N/A";
    }

    public String getDayOfWeek(int day)
    {
        if (day == 1) return "Zondag";
        if (day == 2) return "Maandag";
        if (day == 3) return "Dinsdag";
        if (day == 4) return "Woensdag";
        if (day == 5) return "Donderdag";
        if (day == 6) return "Vrijdag";
        if (day == 7) return "Zaterdag";
        return null;
    }
}
