package com.example.jokedisplayandroidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplay extends AppCompatActivity {
    String joke;
    public static String JOKE_INTENT_STR = "joke_intent_str";
    public static TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        // tv
        textView = findViewById(R.id.textView);

        // get the joke
        joke = getIntent().getExtras().getString(JOKE_INTENT_STR);

        textView.setText(joke);
    }
}
