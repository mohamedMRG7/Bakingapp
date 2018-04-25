package com.example.mohamed.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.recipe.RecipeData;
import com.example.mohamed.bakingapp.utilies.JsonUtilise;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 4/19/18.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder>{

    String json;
    Context context;
    int recipeId;

    public IngredientsAdapter(String json, Context context, int recipeId) {
        this.json = json;
        this.context = context;
        this.recipeId = recipeId;
    }

    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.ingredients_item,parent,false);

        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {

        RecipeData data= JsonUtilise.getIngredients(json,recipeId,position);

        holder.mIngredient.setText(data.getQuantity()+" "+data.getMesure()+" "+data.getIngrediant());

        holder.mNumber.setText(position+1+"");

        if (position==JsonUtilise.getIngredientsCount(json,recipeId)-1)
        holder.mPlus.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return JsonUtilise.getIngredientsCount(json,recipeId);
    }

    class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient)TextView mIngredient;
        @BindView(R.id.tv_ingredient_count)TextView mNumber;
        @BindView(R.id.tv_plus)TextView mPlus;
        public IngredientsAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
