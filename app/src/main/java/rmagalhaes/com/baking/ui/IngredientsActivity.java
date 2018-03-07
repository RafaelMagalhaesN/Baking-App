package rmagalhaes.com.baking.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.models.Recipe;
import rmagalhaes.com.baking.models.RecipeSteps;

import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE;
import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE_POSITION;

public class IngredientsActivity extends AppCompatActivity {

    private Recipe mRecipe;
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(INTENT_RECIPE)) {
                mRecipe = intent.getParcelableExtra(INTENT_RECIPE);
            }

            if (mRecipe != null) {
                setTitle("Ingredients");
                setFragments();
            }
        }
    }

    private void setFragments() {
        FragmentIngredients mFragmentIngredients = new FragmentIngredients();
        mFragmentIngredients.setItems(mRecipe.getIngredients());
        FragmentManager manager = getSupportFragmentManager();

        if (findViewById(R.id.main) != null) {
            manager.beginTransaction()
                    .add(R.id.main, mFragmentIngredients)
                    .commit();
        }
    }
}
