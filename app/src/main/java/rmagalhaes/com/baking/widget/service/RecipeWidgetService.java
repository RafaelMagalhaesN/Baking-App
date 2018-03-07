package rmagalhaes.com.baking.widget.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import rmagalhaes.com.baking.data.RecipeActions;
import rmagalhaes.com.baking.models.Recipe;
import rmagalhaes.com.baking.widget.RecipeListProvider;

/**
 * Created by Rafael Magalhães on 05/03/18.
 */

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Context mContext = this.getApplicationContext();

        ArrayList<Recipe> mRecipes = RecipeActions.getAllFavoritesRecipeItems(mContext);
        return new RecipeListProvider(mContext, intent, mRecipes);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("asdasdsa", "asdasdas");
        return START_STICKY;

    }
}
