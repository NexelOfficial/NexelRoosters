package com.nathandiepeveen.nexelroosters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nathandiepeveen.nexelroosters.tools.ZermeloLogin;

import java.util.HashMap;

import nl.mrwouter.zermelo4j.ZermeloAPI;

public class ActivateApp extends AppCompatActivity {

    Button loginButton;
    TextView koppelCodeText, schoolNameText;
    EditText schoolName, code;
    int codeState = 1;
    int nameState = 1;

    MainActivity main = new MainActivity();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_app);

        schoolName = findViewById(R.id.schoolName);
        code = findViewById(R.id.code);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToken();
            }
        });

        getCodeText();
        getSchoolText();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void setToken()
    {
        new ZermeloLogin(this).execute(schoolName.getText().toString(), code.getText().toString());
    }

    public void goToMain(ZermeloAPI api)
    {
        if (api.getAccessToken()==null)
            return;

        Intent intent = new Intent(ActivateApp.this, MainActivity.class);
        intent.putExtra("ZermeloToken", api.getAccessToken());
        intent.putExtra("ZermeloSchool", api.getSchool());
        startActivity(intent);
    }

    public void getSchoolText()
    {
        schoolName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after)
            {
                schoolNameText = findViewById(R.id.schoolNameText);
                Animation anim_up = AnimationUtils.loadAnimation(ActivateApp.this, R.anim.text_down);
                Animation anim_down = AnimationUtils.loadAnimation(ActivateApp.this, R.anim.text_up);

                if (count >= 0 && codeState == 1) {
                    schoolNameText.startAnimation(anim_up);
                    codeState = 0;
                }
                if (schoolName.getText().toString().length() == 0) {
                    schoolNameText.startAnimation(anim_down);
                    codeState = 1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void getCodeText()
    {
        code.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after)
            {
                koppelCodeText = findViewById(R.id.koppelCodeText);
                Animation anim_up = AnimationUtils.loadAnimation(ActivateApp.this, R.anim.text_down);
                Animation anim_down = AnimationUtils.loadAnimation(ActivateApp.this, R.anim.text_up);

                if (count >= 0 && nameState == 1) {
                    koppelCodeText.startAnimation(anim_up);
                    nameState = 0;
                }
                if (code.getText().toString().length() == 0) {
                    koppelCodeText.startAnimation(anim_down);
                    nameState = 1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

}
