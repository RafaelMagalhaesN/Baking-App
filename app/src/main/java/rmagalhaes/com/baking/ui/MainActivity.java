package rmagalhaes.com.baking.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.adapters.RecipeAdapter;
import rmagalhaes.com.baking.data.RecipeActions;
import rmagalhaes.com.baking.databinding.ActivityMainBinding;
import rmagalhaes.com.baking.models.Recipe;

import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    private ActivityMainBinding mMainBinding;
    @SuppressWarnings("FieldCanBeLocal")
    private static ArrayList<Recipe> mRecipes = new ArrayList<>();
    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initList();
        loadItems();
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
            mRecipes = RecipeActions.getAllRecipeItems(this);
            mAdapter.setItems(mRecipes);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(INTENT_RECIPE, recipe);
        startActivity(intent);
    }

}
