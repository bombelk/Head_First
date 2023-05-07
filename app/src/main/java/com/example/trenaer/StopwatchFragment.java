package com.example.trenaer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.util.Locale;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class StopwatchFragment extends Fragment implements View.OnClickListener {

    // Liczba sekund wyświetlana przez stoper
    private int seconds = 0;
    // Czy stoper działa?
    private boolean running;

    private boolean wasRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SaveInstanceState){
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTimer(layout);
        Button startbutton = layout.findViewById(R.id.start_button);
        startbutton.setOnClickListener(this);
        Button stopbutton = layout.findViewById(R.id.stop_button);
        stopbutton.setOnClickListener(this);
        Button resetbutton = layout.findViewById(R.id.reset_button);
        resetbutton.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.start_button) {
            onClickStart();
        } else if (v.getId() == R.id.stop_button) {
            onClickStop();
        } else if (v.getId() == R.id.reset_button) {
            onClickReset();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Metoda uruchamia stoper po kliknięciu przycisku Start
    private void onClickStart() {
        running = true;
    }

    // Metoda zatrzymuje stoper po kliknięciu przycisku Stop
    private void onClickStop() {
        running = false;
    }

    // Metoda zeruje stoper po kliknięciu przycisku Kasuj
    private void onClickReset() {
        running = false;
        seconds = 0;
    }

    // Wyświetla w stoperze liczbę sekund
    private void runTimer(View view) {
        final TextView timeView = view.findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

}
