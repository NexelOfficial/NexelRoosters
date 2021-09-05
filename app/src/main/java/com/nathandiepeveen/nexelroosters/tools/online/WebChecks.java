package com.nathandiepeveen.nexelroosters.tools.online;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nathandiepeveen.nexelroosters.ActivateApp;
import com.nathandiepeveen.nexelroosters.MainActivity;
import com.nathandiepeveen.nexelroosters.R;
import com.nathandiepeveen.nexelroosters.tools.search.GetSearchItems;

import java.io.IOException;

import nl.mrwouter.zermelo4j.ZermeloAPI;

public class WebChecks
{

    private MainActivity main;

    public WebChecks(MainActivity main)
    {
        this.main = main;
    }

    public void isNetworkConnected()
    {
        TextView noInternet = main.findViewById(R.id.noInternetMessage);
        ImageButton refreshButton = main.findViewById(R.id.refreshButton);
        ConnectivityManager cm = (ConnectivityManager) main.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = cm.getActiveNetworkInfo() != null;
        if (!isConnected)
        {
            noInternet.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.VISIBLE);
        }
        else
        {
            if (main.allStudents.isEmpty())
                new GetSearchItems(main).execute();

            noInternet.setVisibility(View.INVISIBLE);
            refreshButton.setVisibility(View.INVISIBLE);
        }

        if (main.hasInternet != isConnected)
            main.afsprakenLaden(main.currentDay);

        main.hasInternet = isConnected;
    }

    public boolean isLoggedIn()
    {
        if (!doLoginFileCheck())
        {
            Intent intentLogin = new Intent(main, ActivateApp.class);
            main.startActivity(intentLogin);
            return false;
        }
        return true;
    }

    private Boolean doLoginFileCheck()
    {
        try
        {
            if (main.readFile("userData.txt").contains(","))
            {
                String[] data = main.readFile("userData.txt").split(",");
                main.school = data[0];
                main.token = data[1];
                main.api = ZermeloAPI.getAPI(main.school, main.token);
                return true;
            }
            else
            {
                return false;
            }
        } catch (NullPointerException | IOException ex)
        {
            return false;
        }
    }

}
