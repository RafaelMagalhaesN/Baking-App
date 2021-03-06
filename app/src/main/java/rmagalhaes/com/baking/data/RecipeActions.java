package rmagalhaes.com.baking.data;

import android.appwidget.AppWidgetManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.SparseIntArray;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import rmagalhaes.com.baking.data.helpers.RecipeContract;
import rmagalhaes.com.baking.data.retrofit.Fetcher;
import rmagalhaes.com.baking.models.Recipe;
import rmagalhaes.com.baking.models.RecipeObservable;
import rmagalhaes.com.baking.utils.LoaderInternalJSON;
import rmagalhaes.com.baking.widget.RecipeWidgetProvider;

import static rmagalhaes.com.baking.data.helpers.RecipeContract.BASE_CONTENT_URI;
import static rmagalhaes.com.baking.data.helpers.RecipeContract.PATH_RECIPES;

/**
 * Created by Rafael Magalhães on 28/02/18.
 */

public class RecipeActions {

    public static ArrayList<Recipe> getAllLocalRecipeItems(Context context) {
        String fileName = "baking.json";
        String stringify = LoaderInternalJSON.load(context, fileName);
        Recipe[] recipes =  new Gson().fromJson(stringify, Recipe[].class);
        return new ArrayList<>(Arrays.asList(recipes));
    }

    public static void getAllRecipeItems(Context context, RecipeObservable recipeObservable) {
        Fetcher.fetchRecipes(recipeObservable);
    }


    public static ArrayList<Recipe> getAllFavoritesRecipeItems(Context context) {
        Uri RECIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();
        ArrayList<Recipe> allRecipeItems = getAllLocalRecipeItems(context);
        SparseIntArray temp = new SparseIntArray();
        ArrayList<Recipe> favorites = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                RECIPE_URI,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID));
                temp.put(id, id);
            } while (cursor.moveToNext());
        }

        if (temp.size() > 0) {
            for(Recipe recipe: allRecipeItems) {
                int id = temp.get(recipe.getId(), -1);
                if (id >= 0) {
                    favorites.add(recipe);
                }
            }
        }

        if (cursor != null) cursor.close();
        return favorites;
    }

    public static void addFavoriteRecipe(Recipe recipe, Context context) {
        int id = recipe.getId();
        String recipeName = recipe.getName();
        int servings = recipe.getServings();

        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID, id);
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME, recipeName);
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_SERVINGS, servings);
        context.getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI, contentValues);
        updateWidget(context);

    }

    public static void removeFavoriteRecipe(Recipe recipe, Context context) {
        int id = recipe.getId();
        Uri SINGLE_RECIPE_URI = ContentUris.withAppendedId(
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build(), id);
        updateWidget(context);

        context.getContentResolver().delete(SINGLE_RECIPE_URI, null, null);

    }


    public static boolean isFavorite(Recipe recipe, Context context) {
        Uri RECIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();
        Cursor cursor = context.getContentResolver().query(
                RECIPE_URI,
                null,
                RecipeContract.RecipeEntry.COLUMN_RECIPE_ID+" = '"+recipe.getId() +"'" ,
                null,
                null);

        return cursor != null && cursor.getCount() > 0;
    }

    private static void updateWidget(Context context){
        Intent updateWidget = new Intent(context, RecipeWidgetProvider.class);
        updateWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        context.sendBroadcast(updateWidget);
    }

}
