package com.clab.neo.neoclient;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE_TAKE_IMAGE = 9988;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_send).setOnClickListener(v -> {
            findViewById(R.id.group_loading_indicator).setVisibility(View.VISIBLE);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MainActivity.this.runOnUiThread(() -> {
                        findViewById(R.id.group_loading_indicator).setVisibility(View.GONE);
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
            ((ImageView)findViewById(R.id.image_button_src)).setImageBitmap(null);
        });

        findViewById(R.id.image_button_src).setOnClickListener(v -> {
            Intent intent = new Intent().setAction(Intent.ACTION_GET_CONTENT)
                    .setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Get Album"), REQUEST_CODE_TAKE_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKE_IMAGE:
                    String path = getPath(data.getData());
                    Log.d(TAG, "onActivityResult: path = " + path);

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
//                    ((ImageView) findViewById(R.id.image_button_src)).setImageBitmap(bitmap);
                    ((ImageView) findViewById(R.id.image_button_src)).setImageURI(data.getData());
                    break;
                default:
                    break;
            }
        }
    }


    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}
