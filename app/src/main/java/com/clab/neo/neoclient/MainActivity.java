package com.clab.neo.neoclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_send).setOnClickListener(v -> {
            findViewById(R.id.progress_main).setVisibility(View.VISIBLE);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MainActivity.this.runOnUiThread(() -> {
                        findViewById(R.id.progress_main).setVisibility(View.GONE);
                        v.setVisibility(View.GONE);
                        findViewById(R.id.group_initial_text).setVisibility(View.GONE);
                        findViewById(R.id.group_result_text).setVisibility(View.VISIBLE);
                    });
                }
            }, 2000);
        });

        findViewById(R.id.button_retry).setOnClickListener(v -> {
            v.setVisibility(View.GONE);
            findViewById(R.id.group_initial_text).setVisibility(View.VISIBLE);
            findViewById(R.id.group_result_text).setVisibility(View.GONE);
        });
    }
}
