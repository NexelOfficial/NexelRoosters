package com.nathandiepeveen.nexelroosters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nathandiepeveen.nexelroosters.tools.GetFullData;
import com.nathandiepeveen.nexelroosters.tools.afspraken.Schedule;
import com.nathandiepeveen.nexelroosters.tools.animation.ResizeHeigthAnimation;
import com.nathandiepeveen.nexelroosters.tools.buttons.ButtonListeners;
import com.nathandiepeveen.nexelroosters.tools.online.LocalChecks;
import com.nathandiepeveen.nexelroosters.tools.online.WebChecks;
import com.nathandiepeveen.nexelroosters.tools.afspraken.AfsprakenLaden;
import com.nathandiepeveen.nexelroosters.tools.afspraken.AfsprakenToevoegen;
import com.nathandiepeveen.nexelroosters.tools.OnSwipeTouchListener;
import com.nathandiepeveen.nexelroosters.tools.TimeConvertion;
import com.nathandiepeveen.nexelroosters.tools.search.SearchSchedule;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import nl.mrwouter.zermelo4j.ZermeloAPI;

public class MainActivity extends AppCompatActivity {

    private String numberStand = "up";
    private TextView loadingClasses, dateDisplay, dateText, dayDisplay, weekDisplay, numberDisplay, scheduleTitle;
    private ListView classesList = null, allUsers = null;
    private FloatingActionButton homeButton = null;
    private Animation anim_up;
    private CardView searchWindow;

    public String scheduleUser = "~me", token, school;
    public TimeConvertion convert = new TimeConvertion();
    public int currentDay = 0;
    public ZermeloAPI api = null;
    public boolean hasInternet = true;

    public HashMap<String, ArrayList<Schedule>> classesMap = new HashMap<String, ArrayList<Schedule>>();
    public HashMap<String, String> classroomsMap = new HashMap<String, String>();

    public ArrayList<String> allStudents = new ArrayList<>();
    public ArrayAdapter<String> usersAdapter;

    private void initializeItems()
    {
        dateText = findViewById(R.id.scheduleDay);
        scheduleTitle  = findViewById(R.id.scheduleTitle);
        dateDisplay = findViewById(R.id.todayDisplay);
        weekDisplay = findViewById(R.id.weekDisplay);
        dayDisplay = findViewById(R.id.dayDisplay);
        classesList = findViewById(R.id.classesList);
        loadingClasses = findViewById(R.id.loadingClasses);
        allUsers = findViewById(R.id.allUsers);
        searchWindow = findViewById(R.id.searchForUser);
        homeButton = findViewById(R.id.floatingActionButton2);
        anim_up = AnimationUtils.loadAnimation(MainActivity.this, R.anim.move_up);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeItems();

        // Check if the activity has been started from login
        new LocalChecks(MainActivity.this).intentCheck();

        // Check if the user is logged in
        if (!new WebChecks(MainActivity.this).isLoggedIn())
            return;

        classesList.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                afsprakenLaden(Integer.parseInt(dateText.getText().toString()) - 1);
            }
            public void onSwipeLeft() {
                afsprakenLaden(Integer.parseInt(dateText.getText().toString()) + 1);
            }
            public void onSwipeBottom() {
                classesMap.remove(currentDay);
                afsprakenLaden(currentDay);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afsprakenLaden(0);
                homeButton.setVisibility(View.INVISIBLE);
            }
        });

        setupDayButtons(0);
        getNumberText();

        new ButtonListeners(MainActivity.this).listenToBackButton();
        new ButtonListeners(MainActivity.this).listenToLoad();
        new ButtonListeners(MainActivity.this).listenToUserInList();
        new ButtonListeners(MainActivity.this).listenToRefreshButton();

        new SearchSchedule(MainActivity.this).getScheduleFromSearch();
        runNetworkTest.run();

        afsprakenLaden(0);
        for (int i = -5; i < 5; i++)
            new AfsprakenToevoegen(MainActivity.this).execute(String.valueOf(i), scheduleUser);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public Handler handler = new Handler();

    public Runnable runNetworkTest = new Runnable() {
        @Override
        public void run() {
            new WebChecks(MainActivity.this).isNetworkConnected();
            handler.postDelayed(this, 3*1000);
        }
    };

    public void afsprakenLaden(int day)
    {
        loadingClasses.startAnimation(anim_up);
        new AfsprakenLaden(MainActivity.this).execute(String.valueOf(day), scheduleUser);
        dateText.setText(day + "");
        setupDayButtons(day);
        currentDay = day;
        setText(day);
    }

    public void setText(int day)
    {
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE) + day;
        String dateString = date + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        calendar.setTime(convert.ConvertFormattedDateToMilliSeconds(dateString));
        dateString = convert.ConvertMilliSecondsToFormattedMonth(convert.ConvertFormattedDateToMilliSeconds(dateString).getTime());
        if (day == 0) {
            homeButton.setVisibility(View.INVISIBLE);
        } else {
            homeButton.setVisibility(View.VISIBLE);
        }
        dateDisplay.setText(dateString);
        dayDisplay.setText(new GetFullData().getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
        weekDisplay.setText("Week " + calendar.get(Calendar.WEEK_OF_YEAR));
    }

    public void setupDayButtons(final int day)
    {
        int j = 1;
        for (int i = day - 3; i < day + 4; i++)
        {
            int id = getResources().getIdentifier("day" + j, "id", getPackageName());
            final Button btn = findViewById(id);
            String btnText = new TimeConvertion().ConvertDayToFormattedDay(i);
            btn.setText(btnText);
            btn.setTag(i);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int buttonDay = (int) btn.getTag();
                    afsprakenLaden(buttonDay);
                    currentDay = buttonDay;
                }
            });
            j++;
        }
    }

    public void writeToFile(String fileName, String data)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFile(String file) throws IOException {
        FileInputStream fis = openFileInput(file);
        InputStreamReader isr = new InputStreamReader(fis);

        BufferedReader buffer = new BufferedReader(isr);
        StringBuffer stringBugger = new StringBuffer();

        String lines;
        while((lines = buffer.readLine()) != null) {
            stringBugger.append(lines + "\n");
        }
        return stringBugger.toString();
    }

    public void getNumberText() {
        final EditText searchField = findViewById(R.id.editText);
        searchField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                numberDisplay = findViewById(R.id.numberText);
                Animation fadeout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadeout);
                Animation fadein = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);

                if (count >= 0 && numberStand == "up") {
                    allUsers.setVisibility(View.VISIBLE);
                    numberDisplay.startAnimation(fadeout);
                    numberStand = "down";
                    int px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics()));
                    ResizeHeigthAnimation resizeAnimation = new ResizeHeigthAnimation(searchWindow, px);
                    resizeAnimation.setDuration(350);
                    searchWindow.startAnimation(resizeAnimation);
                }
                if (searchField.getText().toString().length() == 0) {
                    allUsers.setVisibility(View.INVISIBLE);
                    numberDisplay.startAnimation(fadein);
                    numberStand = "up";
                    int px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, getResources().getDisplayMetrics()));
                    ResizeHeigthAnimation resizeAnimation = new ResizeHeigthAnimation(searchWindow, px);
                    resizeAnimation.setDuration(350);
                    searchWindow.startAnimation(resizeAnimation);
                }

                try {
                    MainActivity.this.usersAdapter.getFilter().filter(charSequence.toString());
                } catch (NullPointerException ex) {
                    Log.e("FilterException", "Failed to apply filter for usersAdapter");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
