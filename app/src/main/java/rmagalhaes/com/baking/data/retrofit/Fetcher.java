package rmagalhaes.com.baking.data.retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rmagalhaes.com.baking.data.retrofit.entities.Api;
import rmagalhaes.com.baking.data.retrofit.factory.RetrofitApi;
import rmagalhaes.com.baking.models.Recipe;
import rmagalhaes.com.baking.models.RecipeObservable;

/**
 * Created by Rafael Magalhães on 11/03/18.
 */

public class Fetcher {

    public static void fetchRecipes(final RecipeObservable recipeObservable) {
        Api api = RetrofitApi.getRetrofit().create(Api.class);
        api.fetchRecipes().enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    recipeObservable.onSuccess(response.body());
                } else {
                    recipeObservable.onError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                recipeObservable.onError(t);
            }
        });
    }
}
