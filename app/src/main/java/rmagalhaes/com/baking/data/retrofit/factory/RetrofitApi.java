package rmagalhaes.com.baking.data.retrofit.factory;

import retrofit2.converter.gson.GsonConverterFactory;
import rmagalhaes.com.baking.utils.Constants;

/**
 * Created by Rafael Magalhães on 11/03/18.
 */

public class RetrofitApi {

    public static retrofit2.Retrofit getRetrofit() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
