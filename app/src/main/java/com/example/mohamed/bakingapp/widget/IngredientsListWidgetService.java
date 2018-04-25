package com.example.mohamed.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mohamed.bakingapp.MainActivity;
import com.example.mohamed.bakingapp.R;

/**
 * Created by mohamed on 4/22/18.
 */

public class IngredientsListWidgetService extends IntentService {

    public IngredientsListWidgetService() {
        super("IngredientsListWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        if (intent!=null) {
            updateList();
        }
    }

    public static void updateWidgetList(Context context)
    {
        Intent intent=new Intent(context,IngredientsListWidgetService.class);

        context.startService(intent);


    }


    private void updateList()
    {
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);
        int []appIds=appWidgetManager.getAppWidgetIds(new ComponentName(this,FavoriteRecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appIds, R.id.widget_list);

        FavoriteRecipeWidgetProvider.updateWidget(this,appWidgetManager,appIds);
    }
}
