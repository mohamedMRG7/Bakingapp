package com.example.mohamed.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.mohamed.bakingapp.adapters.StepsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 4/18/18.
 */

public class StepsFragment extends Fragment {
    private String json;
    private int recipId;
    @BindView(R.id.rv_steps)RecyclerView mStepsRv;
    LinearLayoutManager manager;


    public StepsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.steps_fragment,container,false);

        manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        if (savedInstanceState!=null)
        {
            json=savedInstanceState.getString(MainActivity.KEY_JSON);
            recipId=savedInstanceState.getInt(MainActivity.KEY_RECIPID);
            manager.onRestoreInstanceState(savedInstanceState.getParcelable(getString(R.string.recycler_position_key)));
        }
        ButterKnife.bind(this,view);

        StepsAdapter adapter;
        if (getActivity().findViewById(R.id.towpan)!=null) {
             adapter = new StepsAdapter(json, getContext(), getActivity().getSupportFragmentManager(), recipId, true);
        }else adapter = new StepsAdapter(json, getContext(), getActivity().getSupportFragmentManager(), recipId, false);
        mStepsRv.setLayoutManager(manager);
        mStepsRv.setAdapter(adapter);



        return view;
    }

    public void setFragmentData(String json,int recipId)
    {
        this.json=json;
        this.recipId=recipId;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(MainActivity.KEY_JSON,json);
        outState.putInt(MainActivity.KEY_RECIPID,recipId);
        outState.putParcelable(getString(R.string.recycler_position_key),manager.onSaveInstanceState());

    }


}
