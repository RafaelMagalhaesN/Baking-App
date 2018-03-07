package rmagalhaes.com.baking.widget;

import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.data.RecipeActions;
import rmagalhaes.com.baking.models.Recipe;
import rmagalhaes.com.baking.ui.DetailActivity;
import rmagalhaes.com.baking.ui.MainActivity;
import rmagalhaes.com.baking.widget.service.RecipeWidgetService;

import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE_ID;

/**
 * Created by Rafael Magalhães on 05/03/18.
 */

public class RecipeListProvider implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Recipe> mRecipes = new ArrayList<>();
    private Context mContext;
    private int appWidgetId;

    public RecipeListProvider(Context mContext, Intent intent, ArrayList<Recipe> recipes) {
        this.mContext = mContext;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        this.mRecipes = recipes;
    }

    private void getFavorites() {
        if (mRecipes != null && mRecipes.size() > 0) mRecipes.clear();
        mRecipes = RecipeActions.getAllFavoritesRecipeItems(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        getFavorites();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }


    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                mContext.getPackageName(), R.layout.item_widget);
        Recipe recipe = mRecipes.get(position);
        remoteView.setTextViewText(R.id.recipe_name, recipe.getName());
        remoteView.setTextViewText(R.id.servings, String.valueOf(recipe.getServings()));
        return remoteView;
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
