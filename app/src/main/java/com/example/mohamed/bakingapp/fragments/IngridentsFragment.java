package com.example.mohamed.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.bakingapp.MainActivity;
import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.adapters.IngredientsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 4/18/18.
 */

public class IngridentsFragment extends Fragment {

    @BindView(R.id.rv_ingredients)RecyclerView mIngreients;
    private String json;
    private int recipId;
    LinearLayoutManager manager;
    public IngridentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.ingredients_fragment,container,false);
        ButterKnife.bind(this,view);
         manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        if (savedInstanceState!=null)
        {
            json=savedInstanceState.getString(MainActivity.KEY_JSON);
            recipId=savedInstanceState.getInt(MainActivity.KEY_RECIPID);
            manager.onRestoreInstanceState(savedInstanceState.getParcelable(getString(R.string.recycler_position_key)));
        }

        IngredientsAdapter adapter=new IngredientsAdapter(json,getContext(),recipId);

        mIngreients.setLayoutManager(manager);
        mIngreients.setAdapter(adapter);



        return view;
    }

    public void setFragmentData(String json,int recipId)
    {
        this.json=json;
        this.recipId=recipId;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(MainActivity.KEY_JSON,json);
        outState.putInt(MainActivity.KEY_RECIPID,recipId);
        outState.putParcelable(getString(R.string.recycler_position_key),manager.onSaveInstanceState());

    }
}
