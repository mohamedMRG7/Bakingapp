package com.example.mohamed.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.bakingapp.MainActivity;
import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.RecipeDetailsActivity;
import com.example.mohamed.bakingapp.recipe.RecipeData;
import com.example.mohamed.bakingapp.utilies.JsonUtilise;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 4/18/18.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder>{


    private String json;
    private Context context;
    private String [] recipeImages=
            {"https://www.recipeboy.com/wp-content/uploads/2016/09/No-Bake-Nutella-Pie.jpg",
    "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2016/2/18/1/FNK_Brownie-Guide-Classic-Brownies_s4x3.jpg.rend.hgtvcom.616.462.suffix/1456176242492.jpeg"
    ,"https://www.browneyedbaker.com/wp-content/uploads/2016/02/yellow-cake-chocolate-frosting-48-600.jpg",
    "https://d2gk7xgygi98cy.cloudfront.net/1820-3-large.jpg"};

    public RecipeListAdapter(Context context) {
        this.context = context;
    }

    public void updateData(String json)
    {
        this.json=json;
        notifyDataSetChanged();
    }

    @Override
    public RecipeListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recipe_list_item,parent,false);

        return new RecipeListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListAdapterViewHolder holder, int position) {

        RecipeData data=JsonUtilise.getRecips(json,position);
        holder.bakingName.setText(data.getName());

        if (data.getImage()!=null && !data.getImage().isEmpty())
            Picasso.get().load(data.getImage()).into(holder.bakingImage);
        else
            Picasso.get().load(recipeImages[position]).into(holder.bakingImage);

    }

    @Override
    public int getItemCount() {
       return JsonUtilise.getRecipsCount(json);
    }

    class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.img_baking_image)ImageView bakingImage;
        @BindView(R.id.tv_baking_name)TextView bakingName;
        public RecipeListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position=getAdapterPosition();
            Intent intent=new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra(MainActivity.KEY_JSON,json);
            intent.putExtra(MainActivity.KEY_RECIPID,position);
            context.startActivity(intent);

        }
    }
}
