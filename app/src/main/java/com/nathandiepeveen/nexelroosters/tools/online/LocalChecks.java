package com.nathandiepeveen.nexelroosters.tools.online;

import android.content.Intent;
import android.os.Bundle;

import com.nathandiepeveen.nexelroosters.MainActivity;

import nl.mrwouter.zermelo4j.ZermeloAPI;

public class LocalChecks {

    private MainActivity main;

    public LocalChecks(MainActivity main) {
        this.main = main;
    }

    public void intentCheck()
    {
        Intent intent = main.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            main.school = (String) bundle.get("ZermeloSchool");
            main.token = (String) bundle.get("ZermeloToken");
            main.api = ZermeloAPI.getAPI(main.school, main.token);
            main.writeToFile("userData.txt", main.api.getSchool() + "," + main.api.getAccessToken());
        }
    }

}
