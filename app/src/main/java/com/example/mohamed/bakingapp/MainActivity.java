package com.example.mohamed.bakingapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.bakingapp.adapters.RecipeListAdapter;
import com.example.mohamed.bakingapp.connection.CheckConnection;
import com.example.mohamed.bakingapp.test.TestIdlingResorce;
import com.example.mohamed.bakingapp.utilies.NetworkUtilise;
import com.example.mohamed.bakingapp.widget.IngredientsListWidgetService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private final String TAG="MainActivity";
    public static final String KEY_JSON="json";
    public static final String KEY_RECIPID="recipId";
    public static final String KEY_SHARED_PREF ="pref";

    LoaderManager.LoaderCallbacks loaderCallbacks=this;
    Context context=this;
    @BindView(R.id.rv_baking_list)RecyclerView mBakingrv;
    @BindView(R.id.layout_noconnection)ConstraintLayout layout_noconnection;
    RecipeListAdapter adapter;
    RecyclerView.LayoutManager manager;
    @Nullable TestIdlingResorce idlingResorce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ButterKnife.bind(this);
        adapter=new RecipeListAdapter(this);

        if (findViewById(R.id.main_twopan)==null)
            manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        else
            manager = new GridLayoutManager(this,3);


        mBakingrv.setLayoutManager(manager);
        mBakingrv.setAdapter(adapter);


        handleConnectionState();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void handleConnectionState()
    {
        String storedJson=getSharedPreferences(KEY_SHARED_PREF,MODE_PRIVATE).getString(KEY_JSON,"");

        if (CheckConnection.isOnline(this)) {
            getLoaderManager().initLoader(1, null, this).forceLoad();
            hideNoconnectoinMessage();

        }
        else if (!CheckConnection.isOnline(this) && storedJson.isEmpty())
        {
            showNoconnectionMessage();
            tryingToReconnect();
            if (idlingResorce!=null)
                idlingResorce.setIdleState(false);

        }else if (!CheckConnection.isOnline(this) && !storedJson.isEmpty())
        {
            adapter.updateData(storedJson);
            hideNoconnectoinMessage();

            Toast.makeText(this, R.string.noconnection_message,Toast.LENGTH_LONG).show();

            if (idlingResorce!=null)
                idlingResorce.setIdleState(true);
        }

    }


    private void showNoconnectionMessage()
    {layout_noconnection.setVisibility(View.VISIBLE);}

    private void hideNoconnectoinMessage()
    {
        layout_noconnection.setVisibility(View.GONE);
    }


    private void tryingToReconnect()
    {
        int delayTime= (int) TimeUnit.SECONDS.toMillis(3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (CheckConnection.isOnline(context))
                {
                    getLoaderManager().initLoader(1, null, loaderCallbacks).forceLoad();
                    hideNoconnectoinMessage();

                }
                else
                    {
                        tryingToReconnect();

                    }


            }
        },delayTime);

    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {

        if (idlingResorce!=null)
            idlingResorce.setIdleState(false);

        return new AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {




                return NetworkUtilise.getJsonString();
            }
        };
    }



    @Override
    public void onLoadFinished(Loader<String> loader, String s) {



        adapter.updateData(s);
        saveJsonString(s);
        IngredientsListWidgetService.updateWidgetList(this);

        if (idlingResorce!=null)
            idlingResorce.setIdleState(true);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }



    public void saveJsonString(String json)
    {
        SharedPreferences sharedPreferences=getSharedPreferences(KEY_SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_JSON,json);
        editor.apply();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResorce()
    {
        if (idlingResorce==null)
            idlingResorce=new TestIdlingResorce();

        return idlingResorce;
    }
}
