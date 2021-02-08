package com.nathandiepeveen.nexelroosters.tools.afspraken;

import android.os.AsyncTask;
import android.os.health.SystemHealthManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nathandiepeveen.nexelroosters.MainActivity;
import com.nathandiepeveen.nexelroosters.R;
import com.nathandiepeveen.nexelroosters.tools.GetFullData;
import com.nathandiepeveen.nexelroosters.tools.TimeConvertion;
import com.nathandiepeveen.nexelroosters.tools.online.WebChecks;
import com.nathandiepeveen.nexelroosters.tools.storage.LocalSchedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.mrwouter.zermelo4j.ZermeloAPI;
import nl.mrwouter.zermelo4j.appointments.Appointment;
import nl.mrwouter.zermelo4j.appointments.AppointmentParticipationException;

public class AfsprakenLaden extends AsyncTask<String, Void, Void> {

    private MainActivity main;

    public AfsprakenLaden(MainActivity main) {
        this.main = main;
    }
    public TimeConvertion convert = new TimeConvertion();

    public ArrayList<Schedule> classesArray = new ArrayList<Schedule>();

    @Override
    protected Void doInBackground(String... data)
    {
        // Check if the day already exists in HashMap
        if (main.classesMap.containsKey(data[0])) {
            classesArray.addAll(main.classesMap.get(data[0]));
            return null;
        }

        // Check if there is an active internet connection
        if (!main.hasInternet)
            return null;

        // Get zermelo API
        ZermeloAPI api = main.api;

        Date startDate = convert.getStartDate(Integer.parseInt(data[0]));
        Date endDate = convert.getStartDate((Integer.parseInt(data[0]))+1);

        List<Appointment> appointments = api.getAppointments(data[1], startDate, endDate, getType());
        if (appointments.size() != 0)
        {
            for (Appointment app : appointments) {
                try {
                    createAppointmentFromZermelo(app);
                } catch (AppointmentParticipationException e) {
                    e.printStackTrace();
                }
            }
            main.classesMap.put(data[0], classesArray);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        ListView classesList = main.findViewById(R.id.classesList);
        TextView loadingClasses = main.findViewById(R.id.loadingClasses);
        ScheduleAdapter classesAdapter = new ScheduleAdapter(main, R.layout.listview_item, classesArray);
        classesList.setAdapter(classesAdapter);

        Animation anim_down = AnimationUtils.loadAnimation(main, R.anim.move_down);
        loadingClasses.startAnimation(anim_down);
        super.onPostExecute(aVoid);
    }

    void createAppointmentFromZermelo(Appointment app) throws AppointmentParticipationException {

        if (!app.getChangeDescription().isEmpty() && !app.isModified())
            if (!app.isNew())
                return;

        StringBuilder teachersString = new StringBuilder(), locationString = new StringBuilder(), subjectsString = new StringBuilder(), groupsString = new StringBuilder();
        List<String> subjectsList = app.getSubjects(), locationList = app.getLocations(), teachersList = app.getTeachers(), groupsList = app.getGroups();
        for (String teacher : teachersList)
            teachersString.append(teacher + " ");
        for (String location : locationList)
            locationString.append(location + " ");
        for (String subject : subjectsList)
            subjectsString.append(new GetFullData().getSubjectFull(subject) + " ");
        for (String group : groupsList)
            groupsString.append(group + " ");

        String startTime = convert.ConvertMilliSecondsToTime(Long.parseLong(app.getStart() + "000"));
        String endTime = convert.ConvertMilliSecondsToTime(Long.parseLong(app.getEnd() + "000"));
        String appHour = app.getStartTimeSlot();

        String toAdd = "" + subjectsString + groupsString + "\n" + locationString + "- " + teachersString + "(" + startTime + " - " + endTime + ")";

        classesArray.add(new Schedule(appHour, toAdd, app.getChangeDescription(), app.isCancelled()));
    }

    private String getType()
    {
        if (main.scheduleUser.equals("~me"))
            return "user";
        if (main.classroomsMap.containsValue(main.scheduleUser))
            return "locationsOfBranch";
        else
            return "user";
    }

}
