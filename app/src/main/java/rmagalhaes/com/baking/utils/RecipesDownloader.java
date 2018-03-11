package rmagalhaes.com.baking.utils;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import rmagalhaes.com.baking.data.RecipeActions;
import rmagalhaes.com.baking.models.Recipe;

/**
 * Created by Rafael Magalhães on 11/03/18.
 */

public class RecipesDownloader {

    final static ArrayList<Recipe> mRecipes = new ArrayList<>();
    private static final int DELAY_MILLIS = 3000;

    public interface DelayerCallback{
        void onDone(ArrayList<Recipe> recipes);
    }

    public static void downloadRecipes(Context context, final DelayerCallback callback,
                                       @Nullable final SimpleIdlingResource idlingResource) {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        // Fill ArrayList with Tea objects
       mRecipes.addAll(RecipeActions.getAllLocalRecipeItems(context));


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(mRecipes);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}
