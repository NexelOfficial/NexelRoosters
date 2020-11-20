package com.nathandiepeveen.nexelroosters.tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.nathandiepeveen.nexelroosters.ActivateApp;
import com.nathandiepeveen.nexelroosters.MainActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import nl.mrwouter.zermelo4j.ZermeloAPI;

public class ZermeloLogin extends AsyncTask<String, Void, ZermeloAPI> {

    public ActivateApp activity;

    public ZermeloLogin(ActivateApp activity){
        this.activity = activity;
    }

    @Override
    protected ZermeloAPI doInBackground(String... gegevens)
    {
        String token = null;
        try {
            token = ZermeloAPI.getAccessToken(gegevens[0], gegevens[1]);
        } catch (IOException e) {
            System.out.println("Error: Could not generate token!");
        }
        ZermeloAPI api = ZermeloAPI.getAPI(gegevens[0], token);
        return api;
    }

    @Override
    protected void onPostExecute(ZermeloAPI api) {
        activity.goToMain(api);
    }

}
