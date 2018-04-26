package com.example.mohamed.bakingapp;

import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayVideoActivity extends AppCompatActivity {

    @BindView(R.id.exo_playvideo)SimpleExoPlayerView mExoPlayerView;
    SimpleExoPlayer mExoPlayer;
    String uri;
    long playerPosition=0;
    private final String KEY_EXOPLAYER_POSITION="exoposition";
    private final String KEY_ISPLAYING="isplaying";
    private boolean isPlaying=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        uri=getIntent().getAction();


        ButterKnife.bind(this);

        if (savedInstanceState!=null)
        {
             playerPosition=savedInstanceState.getLong(KEY_EXOPLAYER_POSITION);
             isPlaying=savedInstanceState.getBoolean(KEY_ISPLAYING);
        }

    }



    private void initializeExoPlayer(Uri uri)
    {
        if (mExoPlayer==null)
        {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);

            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            mExoPlayerView.setPlayer(mExoPlayer);




            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


            MediaSource mediaSource = new ExtractorMediaSource(uri,
                    new DefaultDataSourceFactory(this,Util.getUserAgent(this,"BakingApp")), extractorsFactory, null, null);

            mExoPlayer.seekTo(playerPosition);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isPlaying);

        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

            outState.putLong(KEY_EXOPLAYER_POSITION,playerPosition);
            outState.putBoolean(KEY_ISPLAYING,isPlaying);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeExoPlayer(Uri.parse(uri));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fullScreen();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializeExoPlayer(Uri.parse(uri));

        }
    }

    private void releasePlayer()
    {
        if (mExoPlayer!=null) {
            playerPosition=mExoPlayer.getCurrentPosition();
            isPlaying=mExoPlayer.getPlayWhenReady();

            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void fullScreen() {
        mExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
               );
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }
}
