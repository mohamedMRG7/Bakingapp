package com.example.mohamed.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.bakingapp.PlayVideoActivity;
import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.connection.CheckConnection;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 4/21/18.
 */

public class OneStepFragment extends Fragment implements View.OnClickListener{


    @BindView(R.id.tv_shortdescription)TextView mShortDesc;
    @BindView(R.id.tv_longdescription)TextView mLongDesc;
    @BindView(R.id.img_showvideo)ImageView mShowVideo;
    @BindView(R.id.img_click_to_play)ImageView mClickToPlay;
    String shortDesc;
    String longDesc;
    String videoUrl;
    String imageLink;
    private String KEY_SHORTDESCRIP="short_desc";
    private String KEY_LONGDESC="long_desc";
    private String KEY_VIDEOURL="videourl";
    private String KEY_IMGLINK="imagelink";

    boolean isVideoStarted=false;
    @BindView(R.id.exo_playvideo)SimpleExoPlayerView mExoPlayerView;
    SimpleExoPlayer mExoPlayer;

    long playerPosition=0;
    private final String KEY_EXOPLAYER_POSITION="exoposition";
    private final String KEY_ISPLAYING="isplaying";
    private final String KEY_ISSTARTED="isstarted";
    private boolean isPlaying=true;

    public OneStepFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.one_step_fragment,container,false);
        ButterKnife.bind(this,view);
        if(savedInstanceState!=null)
        {
            imageLink=savedInstanceState.getString(KEY_IMGLINK);
            longDesc=savedInstanceState.getString(KEY_LONGDESC);
            shortDesc=savedInstanceState.getString(KEY_SHORTDESCRIP);
            videoUrl=savedInstanceState.getString(KEY_VIDEOURL);

            playerPosition=savedInstanceState.getLong(KEY_EXOPLAYER_POSITION);
            isPlaying=savedInstanceState.getBoolean(KEY_ISPLAYING);
            isVideoStarted=savedInstanceState.getBoolean(KEY_ISSTARTED);


        }
        mLongDesc.setText(longDesc);
        mShortDesc.setText(shortDesc);
        if (!imageLink.isEmpty())
        Picasso.get().load(imageLink).placeholder(R.drawable.recipe_error_placeholder).error(R.drawable.recipe_error_placeholder).into(mShowVideo);
        else
            mShowVideo.setImageResource(R.drawable.recipe_error_placeholder);

        if (videoUrl.isEmpty())
            mClickToPlay.setVisibility(View.GONE);


        if (isVideoStarted) {
            mExoPlayerView.setVisibility(View.VISIBLE);
            mShowVideo.setVisibility(View.GONE);
            mClickToPlay.setVisibility(View.GONE);
            mLongDesc.setVisibility(View.GONE);
        }

        view.setOnClickListener(this);

        return view;
    }

    public void setFragmentData(String shortDesc,String longDesc,String videoUrl,String imageLink)
    {
        this.shortDesc=shortDesc;
        this.longDesc=longDesc;
        this.videoUrl=videoUrl;
        this.imageLink=imageLink;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMGLINK,imageLink);
        outState.putString(KEY_LONGDESC,longDesc);
        outState.putString(KEY_SHORTDESCRIP,shortDesc);
        outState.putString(KEY_VIDEOURL,videoUrl);

        outState.putLong(KEY_EXOPLAYER_POSITION,playerPosition);
        outState.putBoolean(KEY_ISPLAYING,isPlaying);

        outState.putBoolean(KEY_ISSTARTED,isVideoStarted);

    }



    @Override
    public void onClick(View v) {

        if (CheckConnection.isOnline(getContext())) {

            if (videoUrl != null && !videoUrl.isEmpty()) {

               mClickToPlay.setVisibility(View.GONE);
               mShowVideo.setVisibility(View.GONE);
               mLongDesc.setVisibility(View.GONE);
               mExoPlayerView.setVisibility(View.VISIBLE);

               isVideoStarted=true;
               initializeExoPlayer(Uri.parse(videoUrl));


            } else
                Toast.makeText(getContext(), R.string.no_video_message, Toast.LENGTH_LONG).show();

        }else Toast.makeText(getContext(), R.string.noconnection_message, Toast.LENGTH_LONG).show();

    }

    private void initializeExoPlayer(Uri uri)
    {
        if (mExoPlayer==null)
        {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);

            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            mExoPlayerView.setPlayer(mExoPlayer);




            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


            MediaSource mediaSource = new ExtractorMediaSource(uri,
                    new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(),"BakingApp")), extractorsFactory, null, null);

            mExoPlayer.seekTo(playerPosition);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isPlaying);

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

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
}
