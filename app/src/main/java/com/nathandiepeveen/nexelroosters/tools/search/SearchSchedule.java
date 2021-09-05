package com.nathandiepeveen.nexelroosters.tools.search;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;

import com.nathandiepeveen.nexelroosters.MainActivity;
import com.nathandiepeveen.nexelroosters.R;

public class SearchSchedule
{

    private MainActivity main;

    public SearchSchedule(MainActivity main)
    {
        this.main = main;
    }

    private Button searchButton;
    private EditText searchField;
    private ImageButton backButton, loadUserSchedule;
    private CardView searchBox;

    private void initializeItems()
    {
        searchButton = main.findViewById(R.id.searchButton);
        searchField = main.findViewById(R.id.editText);
        loadUserSchedule = main.findViewById(R.id.loadUserSchedule);
        backButton = main.findViewById(R.id.backButton);
        searchBox = main.findViewById(R.id.searchForUser);
    }

    public void getScheduleFromSearch()
    {
        final Animation fadeOut = AnimationUtils.loadAnimation(main, R.anim.fadeout);
        final Animation fadeIn = AnimationUtils.loadAnimation(main, R.anim.fadein);

        initializeItems();

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (searchField.getText().toString().length() != 0)
                {
                    main.scheduleUser = searchField.getText().toString();
                    backButton.startAnimation(fadeIn);
                    backButton.setEnabled(true);
                }
                else
                {
                    main.scheduleUser = "~me";
                    backButton.startAnimation(fadeOut);
                    backButton.setEnabled(false);
                }
                main.classesMap.clear();
                loadUserSchedule.setImageResource(R.drawable.search_loop);
                searchBox.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager) main.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                main.afsprakenLaden(main.currentDay);
            }
        });
    }
}
