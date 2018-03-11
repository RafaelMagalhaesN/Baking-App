package rmagalhaes.com.baking.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.adapters.RecipeAdapter;
import rmagalhaes.com.baking.data.RecipeActions;
import rmagalhaes.com.baking.databinding.ActivityMainBinding;
import rmagalhaes.com.baking.models.Recipe;
import rmagalhaes.com.baking.models.RecipeObservable;
import rmagalhaes.com.baking.utils.RecipesDownloader;
import rmagalhaes.com.baking.utils.SimpleIdlingResource;

import static rmagalhaes.com.baking.utils.Constants.INTENT_RECIPE;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener, RecipesDownloader.DelayerCallback {

    private ActivityMainBinding mMainBinding;
    @SuppressWarnings("FieldCanBeLocal")
    private static ArrayList<Recipe> mRecipes = new ArrayList<>();
    private RecipeAdapter mAdapter;
    private TextView error;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Binding not works with this textView and idk the cause... (I try: Clean project, Rebuild, etcs), next time I will use ButterKnife :(
        error = (TextView) findViewById(R.id.errorInternet);
        //mMainBinding.errorInternet.setVisibility(View.VISIBLE);

        initList();
        getIdlingResource();
        loadItems();

    }

    @Override
    protected void onStart() {
        RecipesDownloader.downloadRecipes(this, MainActivity.this, mIdlingResource);
        super.onStart();

    }

    private void initList() {
        int spanCount = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);

        mMainBinding.recyclerView.setLayoutManager(gridLayoutManager);
        mMainBinding.recyclerView.setHasFixedSize(true);

        mAdapter = new RecipeAdapter(this);
        mMainBinding.recyclerView.setAdapter(mAdapter);
    }

    private void loadItems() {
        if (mAdapter != null) {
            RecipeActions.getAllRecipeItems(this, new RecipeObservable() {
                @Override
                public void onSuccess(ArrayList<Recipe> recipes) {
                    mRecipes = recipes;
                    mAdapter.setItems(mRecipes);
                    onSuccessVisible();
                }

                @Override
                public void onError() {
                    onErrorVisible();
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                    onErrorVisible();
                }
            });
        }
    }

    public void onErrorVisible() {
        mMainBinding.recyclerView.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }

    public void onSuccessVisible() {
        mMainBinding.recyclerView.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(INTENT_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public void onDone(ArrayList<Recipe> recipes) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
    }
}
