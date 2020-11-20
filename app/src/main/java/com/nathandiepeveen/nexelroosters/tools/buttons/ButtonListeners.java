package com.nathandiepeveen.nexelroosters.tools.buttons;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.cardview.widget.CardView;

import com.nathandiepeveen.nexelroosters.MainActivity;
import com.nathandiepeveen.nexelroosters.R;

import java.util.ArrayList;

public class ButtonListeners {

    private MainActivity main;

    public ButtonListeners(MainActivity main) {
        this.main = main;
    }

    private ImageButton loadUserSchedule, backButton, refreshButton;
    private ListView allStudents, classesList;
    private CardView searchBox;
    private EditText searchField;

    private void initializeItems()
    {
        backButton = main.findViewById(R.id.backButton);
        searchBox = main.findViewById(R.id.searchForUser);
        refreshButton = main.findViewById(R.id.refreshButton);
        loadUserSchedule = main.findViewById(R.id.loadUserSchedule);
        searchField = main.findViewById(R.id.editText);
        allStudents = main.findViewById(R.id.allUsers);
        classesList = main.findViewById(R.id.classesList);
    }

    public void listenToBackButton()
    {
        initializeItems();
        final Animation fadeOut = AnimationUtils.loadAnimation(main, R.anim.fadeout);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backButton.startAnimation(fadeOut);
                backButton.setEnabled(false);
                main.scheduleUser = "~me";
                main.classesMap.clear();
                main.afsprakenLaden(main.currentDay);
            }
        });
    }

    public void listenToRefreshButton()
    {
        initializeItems();
        final Animation fadeOut = AnimationUtils.loadAnimation(main, R.anim.fadeout);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshButton.setVisibility(View.INVISIBLE);
                main.afsprakenLaden(main.currentDay);
            }
        });
    }

    public void listenToLoad()
    {
        initializeItems();

        loadUserSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchBox.getVisibility() == View.VISIBLE) {
                    searchBox.setVisibility(View.INVISIBLE);
                    loadUserSchedule.setImageResource(R.drawable.search_loop);
                    InputMethodManager imm = (InputMethodManager) main.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    searchBox.setVisibility(View.VISIBLE);
                    loadUserSchedule.setImageResource(R.drawable.cross_icon);
                }
            }
        });
    }

    public void listenToUserInList()
    {
        initializeItems();

        allStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                String textAtPosition = allStudents.getItemAtPosition(position).toString();
                if (main.classroomsMap.containsKey(textAtPosition)) {
                    String classroomId = main.classroomsMap.get(textAtPosition.trim());
                    searchField.setText(classroomId + "");
                    return;
                }

                ArrayList<String> searchListArray = new ArrayList<>();
                for (int i = 0; i < allStudents.getCount(); i++){
                    searchListArray.add(textAtPosition.toString());
                }

                String studentText = searchListArray.get(position);
                String[] studentTextSplit = studentText.split("\\(");
                searchField.setText(studentTextSplit[1].replace(")", ""));
            }
        });
    }

}
