package com.nathandiepeveen.nexelroosters.tools.storage;

import android.util.Log;

import com.nathandiepeveen.nexelroosters.MainActivity;
import com.nathandiepeveen.nexelroosters.tools.TimeConvertion;

import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalSchedule {

    private MainActivity main;

    public LocalSchedule(MainActivity main) {
        this.main = main;
    }

    private String savedClasses = null;

    public void saveClassesToFile(String day, ArrayList<String> classForDay)
    {
        try {
            savedClasses = main.readFile("OfflineSchedule.txt");
            System.out.println(savedClasses);
        } catch (Exception ex) {
            Log.e("FileReadException", "Could not read that file!");
            savedClasses = null;
        }

        HashMap<Integer, String> linesToSave = new HashMap<>();
        if (!savedClasses.isEmpty()) {
            String[] allLines = savedClasses.split("\n");

            for (String line : allLines) {
                String[] dataOfDay = line.split("|");
                int dayMillis = Integer.parseInt(dataOfDay[0]);
                linesToSave.put(dayMillis, dataOfDay[1]);
            }
        }

        int inputDayInt = Integer.parseInt(day);
        int currentDayMillis = (int) new TimeConvertion().getStartDate(inputDayInt).getTime();
        linesToSave.put(currentDayMillis, classForDay.toString());
        System.out.println(classForDay.toString());

        String toSave = "";
        for (int key : linesToSave.keySet())
        {
            toSave += key + "|" + linesToSave.get(key) + "\n";
        }

        main.writeToFile("OfflineSchedule.txt", toSave);
    }

}
