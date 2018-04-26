package com.example.mohamed.bakingapp;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mohamed.bakingapp.adapters.DetailsPagerAdapter;
import com.example.mohamed.bakingapp.fragments.IngridentsFragment;
import com.example.mohamed.bakingapp.fragments.StepsFragment;
import com.example.mohamed.bakingapp.utilies.JsonUtilise;
import com.example.mohamed.bakingapp.widget.IngredientsListWidgetService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    @Nullable @BindView(R.id.details_tap)TabLayout mTap;
    @Nullable @BindView(R.id.details_viewpager)ViewPager mPager;
    @Nullable @BindView(R.id.bt_ingredients)Button mShowIngredients;
    @BindView(R.id.toolbar)Toolbar toolbar;
    String json;
    int recipId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        json=getIntent().getStringExtra(MainActivity.KEY_JSON);
         recipId=getIntent().getIntExtra(MainActivity.KEY_RECIPID,0);


         Log.e("isnull",String.valueOf(json==null)+String.valueOf(recipId));

        String recipeName=JsonUtilise.getRecips(json,recipId).getName();
        setTitle(recipeName);

        if ((findViewById(R.id.towpan)!=null)){

            twoPaneSetup();
        }

        if (findViewById(R.id.towpan)==null) {

            onePaneSetup();
        }
    }


    private void twoPaneSetup()
    {
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setFragmentData(json, recipId);

        if (getSupportFragmentManager().findFragmentByTag("steps")==null)
            getSupportFragmentManager().beginTransaction().add(R.id.steps_container, stepsFragment,"steps").commit();

        mShowIngredients.setOnClickListener(this);
    }


    public void onePaneSetup()
    {
        TabLayout.Tab ingredientsTap = mTap.newTab();
        ingredientsTap.setText(R.string.ingredients);
        ingredientsTap.setIcon(R.drawable.ic_harvest);

        TabLayout.Tab stepsTap = mTap.newTab();
        stepsTap.setIcon(R.drawable.ic_steps);
        stepsTap.setText(R.string.steps);

        mTap.addTab(ingredientsTap);
        mTap.addTab(stepsTap);


        DetailsPagerAdapter adapter = new DetailsPagerAdapter(getSupportFragmentManager(), mTap.getTabCount(), json, recipId);
        mPager.setAdapter(adapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTap));


        mTap.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        if (isFav())
            menu.getItem(0).setIcon(R.drawable.ic_selected);
        else
            menu.getItem(0).setIcon(R.drawable.ic_nonselected);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch (id)
        {
            case R.id.menu_favourite:
                addToFav(recipId);
                IngredientsListWidgetService.updateWidgetList(this);
                invalidateOptionsMenu();

                break;
            case android.R.id.home:
                finish();


        }

        return true;
    }

    private void addToFav(int recipId)
    {
        SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.KEY_SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(MainActivity.KEY_RECIPID,recipId);
        editor.apply();
    }

    private boolean isFav()
    {
        int recipId=getSharedPreferences(MainActivity.KEY_SHARED_PREF,MODE_PRIVATE).getInt(MainActivity.KEY_RECIPID,1000);

        return this.recipId==recipId;
    }
    @Override
    public void onClick(View v) {

        int id=v.getId();
        if (id==R.id.bt_ingredients)
        {

            IngridentsFragment ingridentsFragment=new IngridentsFragment();
            ingridentsFragment.setFragmentData(json,recipId);
            getSupportFragmentManager().beginTransaction().replace(R.id.details_container,ingridentsFragment).commit();


        }
    }
}
