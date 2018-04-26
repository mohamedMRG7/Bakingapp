package com.example.mohamed.bakingapp.fragments;

import android.content.Intent;
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
        }
        mLongDesc.setText(longDesc);
        mShortDesc.setText(shortDesc);
        if (!imageLink.isEmpty())
        Picasso.get().load(imageLink).placeholder(R.drawable.recipe_error_placeholder).error(R.drawable.recipe_error_placeholder).into(mShowVideo);
        else
            mShowVideo.setImageResource(R.drawable.recipe_error_placeholder);

        if (videoUrl.isEmpty())
            mClickToPlay.setVisibility(View.GONE);


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
    }



    @Override
    public void onClick(View v) {

        if (CheckConnection.isOnline(getContext())) {

            if (videoUrl != null && !videoUrl.isEmpty()) {
                Intent intent = new Intent(getContext(), PlayVideoActivity.class);
                intent.setAction(videoUrl);
                getContext().startActivity(intent);
            } else
                Toast.makeText(getContext(), R.string.no_video_message, Toast.LENGTH_LONG).show();

        }else Toast.makeText(getContext(), R.string.noconnection_message, Toast.LENGTH_LONG).show();

    }
}
