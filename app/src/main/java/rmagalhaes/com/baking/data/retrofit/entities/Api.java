package rmagalhaes.com.baking.data.retrofit.entities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rmagalhaes.com.baking.models.Recipe;

/**
 * Created by Rafael Magalhães on 11/03/18.
 */

public interface Api {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> fetchRecipes();
}
