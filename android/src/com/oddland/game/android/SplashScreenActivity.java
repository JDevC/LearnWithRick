package com.oddland.game.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;
/** Clase encargada de mostrar la activity de Splash Screen (disponible sólo en Android)**/
public class SplashScreenActivity extends Activity{

    private static final long SPLASH_SCREEN_DELAY = 4000; // Duración de la splash screen en pantalla

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Determina la orientación de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Oculta la barra de título de la aplicación
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_screen);
        // 
        TimerTask task = new TimerTask(){
            @Override
            public void run(){
                // Inicia el lanzador de android
                Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, AndroidLauncher.class);
                startActivity(mainIntent);
                // Se impide el regreso a esta pantalla una vez se haya salido de ella
                finish();
            }
        };
        // Se simula el proceso de carga de la aplicación
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}