package com.nathandiepeveen.nexelroosters.tools.search;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nathandiepeveen.nexelroosters.MainActivity;
import com.nathandiepeveen.nexelroosters.R;

import java.util.ArrayList;
import java.util.Collections;

import nl.mrwouter.zermelo4j.ZermeloAPI;

public class GetSearchItems extends AsyncTask<Void, Void, Void> {

    private MainActivity main;

    public GetSearchItems(MainActivity main) {
        this.main = main;
    }

    private ZermeloAPI api;
    private String schoolInYears;

    @Override
    protected Void doInBackground(Void... voids) {

        this.api = main.api;

        try {
            this.schoolInYears = String.valueOf(getLatestSchoolYear());
            main.allStudents = api.getUsers(schoolInYears).getStudentsAsArray();
            main.allStudents.addAll(api.getUsers(schoolInYears).getEmployeesAsArray());
            main.usersAdapter = new ArrayAdapter<>(main, android.R.layout.simple_list_item_1, main.allStudents);
            addClassroomsToMap();
        } catch(NullPointerException ex) {
            Log.e("SearchResultException", "One or more search lists failed to load.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ListView allUsers = main.findViewById(R.id.allUsers);
        allUsers.setAdapter(main.usersAdapter);

        super.onPostExecute(aVoid);
    }

    private void addClassroomsToMap()
    {
        ArrayList<String> classRoomsAsText = api.getClassrooms(schoolInYears).getClassRoomsAsArray();

        for (String classroomText : classRoomsAsText)
        {
            String[] idAndText = classroomText.split(", ");
            String id = idAndText[0];
            String classroom = idAndText[1];
            main.classroomsMap.put(classroom, id);
            main.allStudents.add(classroom);
        }
    }

    private int getLatestSchoolYear()
    {
        String[] allYears = api.getUser().getSchoolInSchoolYears().toString().replace("[", "").replace("]", "").split(",");
        ArrayList<Integer> allYearsInt = new ArrayList<>();
        for (String year : allYears)
        {
            allYearsInt.add(Integer.parseInt(year));
        }
        return Collections.max(allYearsInt);
    }

}
