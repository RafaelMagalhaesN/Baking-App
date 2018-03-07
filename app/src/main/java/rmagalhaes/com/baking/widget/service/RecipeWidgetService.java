package rmagalhaes.com.baking.widget.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import rmagalhaes.com.baking.data.RecipeActions;
import rmagalhaes.com.baking.models.Recipe;
import rmagalhaes.com.baking.widget.RecipeListProvider;
import rmagalhaes.com.baking.widget.RecipeWidgetProvider;

/**
 * Created by Rafael Magalhães on 05/03/18.
 */

public class RecipeWidgetService extends RemoteViewsService {
    private ArrayList<Recipe> mRecipes = new ArrayList<>();
    private Context mContext;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        mContext = this.getApplicationContext();

        mRecipes = RecipeActions.getAllFavoritesRecipeItems(mContext);
        return new RecipeListProvider(mContext, intent, mRecipes);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("asdasdsa", "asdasdas");
        return START_STICKY;

    }
}
