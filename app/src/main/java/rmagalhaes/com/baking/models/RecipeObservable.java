package rmagalhaes.com.baking.models;

import java.util.ArrayList;

/**
 * Created by Rafael Magalhães on 11/03/18.
 */

public interface RecipeObservable {
    void onSuccess(ArrayList<Recipe> recipes);
    void onError();
    void onError(Throwable throwable);
}
