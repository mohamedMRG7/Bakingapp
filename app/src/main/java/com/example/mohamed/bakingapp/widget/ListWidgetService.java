package com.example.mohamed.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mohamed.bakingapp.MainActivity;
import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.recipe.RecipeData;
import com.example.mohamed.bakingapp.utilies.JsonUtilise;

/**
 * Created by mohamed on 4/22/18.
 */

public class ListWidgetService extends RemoteViewsService
{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListViewRemotsFactory(this.getApplicationContext());
    }
}


 class ListViewRemotsFactory implements RemoteViewsService.RemoteViewsFactory{

    Context context;
    String json;
    int recipId;

     public ListViewRemotsFactory(Context context) {
         this.context = context;
     }

     @Override
    public void onCreate() {

     }

    @Override
    public void onDataSetChanged() {
         recipId=context.getSharedPreferences(MainActivity.KEY_SHARED_PREF,Context.MODE_PRIVATE).getInt(MainActivity.KEY_RECIPID,0);
         json=context.getSharedPreferences(MainActivity.KEY_SHARED_PREF,Context.MODE_PRIVATE).getString(MainActivity.KEY_JSON,"");
    }

    @Override
    public void onDestroy() {

    }


    @Override
    public int getCount() {
        return JsonUtilise.getIngredientsCount(json,recipId);
    }


    @Override
    public RemoteViews getViewAt(int position) {


        RecipeData data=JsonUtilise.getIngredients(json,recipId,position);

        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget_list_item);

        remoteViews.setTextViewText(R.id.tv_ingredient,data.getQuantity()+" "+data.getMesure()+" "+ data.getIngrediant());
        remoteViews.setTextViewText(R.id.tv_ingredient_count,String.valueOf(position+1));

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
