package com.example.mohamed.bakingapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.bakingapp.PlayVideoActivity;
import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.connection.CheckConnection;
import com.example.mohamed.bakingapp.fragments.IngridentsFragment;
import com.example.mohamed.bakingapp.fragments.OneStepFragment;
import com.example.mohamed.bakingapp.recipe.RecipeData;
import com.example.mohamed.bakingapp.utilies.JsonUtilise;
import com.google.android.exoplayer2.C;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by mohamed on 4/19/18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>{


    String json;
    Context context;
    int recipeId;
    boolean isTowPan;
    FragmentManager fm;
    private String [] recipeImages=
            {"https://www.recipeboy.com/wp-content/uploads/2016/09/No-Bake-Nutella-Pie.jpg",
                    "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2016/2/18/1/FNK_Brownie-Guide-Classic-Brownies_s4x3.jpg.rend.hgtvcom.616.462.suffix/1456176242492.jpeg"
                    ,"https://www.browneyedbaker.com/wp-content/uploads/2016/02/yellow-cake-chocolate-frosting-48-600.jpg",
                    "https://d2gk7xgygi98cy.cloudfront.net/1820-3-large.jpg"};

    public StepsAdapter(String json, Context context, FragmentManager fm, int recipeId, boolean isTowPan) {
        this.json = json;
        this.context = context;
        this.recipeId = recipeId;
        this.isTowPan=isTowPan;
        this.fm=fm;
    }

    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.steps_item,parent,false);



        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {

        RecipeData data = JsonUtilise.getSteps(json,recipeId,position);

        if (!isTowPan) {
            holder.mLongDesc.setText(data.getDecription());
            holder.mShortDesc.setText(data.getShortDescrption());

            if (data.getImage() != null && !data.getImage().isEmpty())
                Picasso.get().load(data.getImage()).into(holder.mShowVideo);
            else
                Picasso.get().load(recipeImages[recipeId]).into(holder.mShowVideo);
        }
        else
            {
                holder.mLongDesc.setVisibility(View.GONE);
                holder.mPlayIcon.setVisibility(View.GONE);
                holder.mShortDesc.setText(data.getShortDescrption());

            }
    }

    @Override
    public int getItemCount() {
        return JsonUtilise.getStepsCount(json,recipeId);
    }

    class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_shortdescription)TextView mShortDesc;
        @BindView(R.id.tv_longdescription)TextView mLongDesc;
        @BindView(R.id.img_showvideo)ImageView mShowVideo;
        @BindView(R.id.playicon_layout)FrameLayout mPlayIcon;

        public StepsAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int position=getAdapterPosition();
            int id=view.getId();
            RecipeData data = JsonUtilise.getSteps(json,recipeId,position);
            if (!isTowPan) {
                if (CheckConnection.isOnline(context)) {
                    if (data.getVideoUrl() != null && !data.getVideoUrl().isEmpty()) {
                        Intent intent = new Intent(context, PlayVideoActivity.class);
                        intent.setAction(data.getVideoUrl());
                        context.startActivity(intent);
                    } else
                        Toast.makeText(context, R.string.no_video_message, Toast.LENGTH_LONG).show();

                }else Toast.makeText(context, R.string.noconnection_message, Toast.LENGTH_LONG).show();
            }
            else
                {

                        OneStepFragment oneStepFragment = new OneStepFragment();
                        oneStepFragment.setFragmentData(data.getShortDescrption(), data.getDecription(), data.getVideoUrl(), recipeImages[recipeId]);
                        fm.beginTransaction().replace(R.id.details_container, oneStepFragment).commit();




                }
        }
    }
}
