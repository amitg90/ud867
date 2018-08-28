package com.udacity.gradle.builditbigger;

import android.content.Context;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnReceiveClickListener {
    private Button btn;
    private ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;
    private Intent intent;
    private Boolean loaded = false;

    public MainActivityFragment() {
        Log.e("Amit", "FREE FRAMGENT TRIGGERED");
    }

    public void createInterstitialAd() {
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.e("Amit", "onAdFailedToLoad");
                startActivity(intent);
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.e("Amit", "onAdClosed");
                startActivity(intent);
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        btn = root.findViewById(R.id.btn);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke(v);
            }
        });

        /* Insten ADs */
        MobileAds.initialize(getContext(),
                "ca-app-pub-3940256099942544/1033173712");

        createInterstitialAd();

        // Standard AD
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    public void tellJoke(View view) {
        progressBar.setVisibility(View.VISIBLE);

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            loaded = true;
        } else {
            Log.e("Amit", "The interstitial wasn't loaded yet.");
        }

        EndPointAsyncTask asyncTask = (EndPointAsyncTask) new EndPointAsyncTask();
        asyncTask.execute(new Pair<OnReceiveClickListener, String>(this, "sayHi"));
    }

    @Override
    public void onAsyncTaskDone(String str) {
        progressBar.setVisibility(View.GONE);

        intent = new Intent(getContext(), JokeDisplay.class);
        intent.putExtra(JokeDisplay.JOKE_INTENT_STR, str);

        if (loaded == false) {
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loaded = false;
        createInterstitialAd();
    }
}
