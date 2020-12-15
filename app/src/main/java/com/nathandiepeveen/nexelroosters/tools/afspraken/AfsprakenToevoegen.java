package com.nathandiepeveen.nexelroosters.tools.afspraken;

import android.os.AsyncTask;

import com.nathandiepeveen.nexelroosters.MainActivity;
import com.nathandiepeveen.nexelroosters.R;
import com.nathandiepeveen.nexelroosters.tools.online.WebChecks;
import com.nathandiepeveen.nexelroosters.tools.storage.LocalSchedule;

import java.util.Date;
import java.util.List;

import nl.mrwouter.zermelo4j.ZermeloAPI;
import nl.mrwouter.zermelo4j.appointments.Appointment;
import nl.mrwouter.zermelo4j.appointments.AppointmentParticipationException;

public class AfsprakenToevoegen extends AsyncTask<String, Void, Void> {

    private MainActivity main;

    public AfsprakenToevoegen(MainActivity main) {
        this.main = main;
    }

    AfsprakenLaden laden = new AfsprakenLaden(main);

    @Override
    protected Void doInBackground(String... data)
    {
        // Check if there is an active internet connection
        if (!main.hasInternet)
            return null;

        // Create token and load from Zermelo
        ZermeloAPI api = main.api;

        Date startDate = laden.convert.getStartDate(Integer.parseInt(data[0]));
        Date endDate = laden.convert.getStartDate((Integer.parseInt(data[0]))+1);

        List<Appointment> appointments = api.getAppointments(data[1], startDate, endDate, getType());
        if (appointments.size() != 0) {
            for (Appointment app : appointments) {
                try {
                    laden.createAppointmentFromZermelo(app);
                } catch (AppointmentParticipationException e) {
                    e.printStackTrace();
                }
            }
            main.classesMap.put(data[0], laden.classesArray);
        }

        return null;
    }

    private String getType()
    {
        if (main.scheduleUser.equals("~me"))
            return "user";
        if (main.classroomsMap.containsKey(main.scheduleUser))
            return "locationsOfBranch";
        else
            return "user";
    }

}
