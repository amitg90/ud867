package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jokedisplayandroidlib.JokeDisplay;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnReceiveClickListener {
    private Button btn;
    private ProgressBar progressBar;

    public MainActivityFragment() {
        Log.e("Amit", "PAID FRAMGENT TRIGGERED");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_activity, container, false);

        btn = root.findViewById(R.id.btn);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke(v);
            }
        });

        return root;
    }

    public void tellJoke(View view) {
        progressBar.setVisibility(View.VISIBLE);

        EndPointAsyncTask asyncTask = (EndPointAsyncTask) new EndPointAsyncTask();
        asyncTask.execute(new Pair<OnReceiveClickListener, String>(this, "sayHi"));
    }

    @Override
    public void onAsyncTaskDone(String str) {
        progressBar.setVisibility(View.GONE);

        Intent intent = new Intent(getContext(), JokeDisplay.class);
        intent.putExtra(JokeDisplay.JOKE_INTENT_STR, str);
        startActivity(intent);
    }
}
