package com.nathandiepeveen.nexelroosters.tools.search;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nathandiepeveen.nexelroosters.MainActivity;
import com.nathandiepeveen.nexelroosters.R;

import java.util.ArrayList;
import java.util.Collections;

import nl.mrwouter.zermelo4j.ZermeloAPI;

public class AddYears extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemSelectedListener
{
    private MainActivity main;

    public AddYears(MainActivity main)
    {
        this.main = main;
    }

    private ZermeloAPI api;
    private String schoolInYears;
    private Spinner schoolYears;

    @Override
    protected Void doInBackground(Void... voids)
    {
        this.schoolYears = main.findViewById(R.id.schoolYear);
        this.api = main.api;
        setSchoolYears();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        schoolYears.setAdapter(main.yearsAdapter);
        schoolYears.setOnItemSelectedListener(this);
        schoolYears.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        new GetSearchItems(this.main).execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    private void setSchoolYears()
    {
        Object schoolInSchoolYears = api.getUser().getSchoolInSchoolYears();

        if (schoolInSchoolYears == null)
            return;

        String[] allYears = schoolInSchoolYears.toString().replace("[", "").replace("]", "").split(",");
        main.yearsAdapter = new ArrayAdapter<>(this.main, android.R.layout.simple_spinner_dropdown_item, allYears);
    }
}
