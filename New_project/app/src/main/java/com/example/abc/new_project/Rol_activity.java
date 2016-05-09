package com.example.abc.new_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Rol_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rol_activity);
        Intent temp = getIntent();
        String rol = temp.getStringExtra("Keygen");


        ImageView a = (ImageView)findViewById(R.id.imageView2);
        switch (rol){
            case "Civilians":{
                a.setImageResource(R.drawable.citizen);
                break;
            }
            case "Don": {
               a.setImageResource(R.drawable.don);
                break;
            }
            case "Sheriff":
                 a.setImageResource(R.drawable.detective);
                break;
            case "Doctor":
                a.setImageResource(R.drawable.doctor);
                break;
            case "Mafia" +
                    "":
                a.setImageResource(R.drawable.mafia);
                break;
        }
    }
}