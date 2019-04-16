package com.clab.neo.neoclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_send).setOnClickListener(v -> {
            v.setVisibility(View.GONE);
            findViewById(R.id.text_view_plain1).setVisibility(View.GONE);
            findViewById(R.id.group_result_text).setVisibility(View.VISIBLE);
        });
    }
}
