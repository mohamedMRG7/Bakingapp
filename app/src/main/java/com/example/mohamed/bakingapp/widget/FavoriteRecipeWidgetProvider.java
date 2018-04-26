package com.example.mohamed.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.mohamed.bakingapp.MainActivity;
import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.RecipeDetailsActivity;
import com.example.mohamed.bakingapp.utilies.JsonUtilise;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteRecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views;
        if (isIngredientChoosed(context)) {
            views = getIngredientListRemoteView(context);

            String json=context.getSharedPreferences(MainActivity.KEY_SHARED_PREF,Context.MODE_PRIVATE).getString(MainActivity.KEY_JSON,"");
            int recipId=context.getSharedPreferences(MainActivity.KEY_SHARED_PREF,Context.MODE_PRIVATE).getInt(MainActivity.KEY_RECIPID,0);
            String recipName= JsonUtilise.getRecips(json,recipId).getName();
            views.setTextViewText(R.id.tv_recip_name,recipName);
            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra(MainActivity.KEY_JSON,json);
            intent.putExtra(MainActivity.KEY_RECIPID,recipId);
            PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.tv_recip_name, mainPendingIntent);
        }
        else views=new RemoteViews(context.getPackageName(),R.layout.widget_empty_view);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {



        // Enter relevant functionality for when the first widget is created
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        IngredientsListWidgetService.updateWidgetList(context);

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    private static RemoteViews getIngredientListRemoteView(Context context)
    {
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.favorite_recipe_widget);

        Intent intent=new Intent(context,ListWidgetService.class);
        remoteViews.setRemoteAdapter(R.id.widget_list,intent);




        return remoteViews;
    }


    private static boolean isIngredientChoosed(Context context)
    {
      return context.getSharedPreferences(MainActivity.KEY_SHARED_PREF,Context.MODE_PRIVATE).contains(MainActivity.KEY_RECIPID);
    }

}

