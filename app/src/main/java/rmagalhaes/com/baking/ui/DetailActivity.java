package rmagalhaes.com.baking.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.adapters.StepsAdapter;
import rmagalhaes.com.baking.data.RecipeActions;
import rmagalhaes.com.baking.models.Recipe;
import rmagalhaes.com.baking.models.RecipeSteps;

import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE;
import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE_ID;
import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE_POSITION;
import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE_STEPS;

public class DetailActivity extends AppCompatActivity implements StepsAdapter.StepsClickListener {


    private Recipe mRecipe;
    private FragmentSteps mDescriptionFragment;
    private FragmentPlayer mPlayerFragment;
    private FrameLayout mContent;
    private FrameLayout mIngredientsContent;
    private static LastFragment mLastFragment = LastFragment.INGREDIENTS;

    private enum LastFragment {
        PLAYER(), INGREDIENTS()
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mIngredientsContent = findViewById(R.id.ingredients);
        mContent = findViewById(R.id.content);


        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(INTENT_RECIPE)) {
                mRecipe = intent.getParcelableExtra(INTENT_RECIPE);
                if (mRecipe != null) {
                    String ingredientsStep = "Ingredients";
                    RecipeSteps introductionStep = new RecipeSteps(-1, ingredientsStep, ingredientsStep);
                    mRecipe.getSteps().add(0, introductionStep);
                }
            }

            if (mRecipe == null && intent != null && intent.hasExtra(INTENT_RECIPE_ID)) {
                int id = intent.getIntExtra(INTENT_RECIPE_ID, -1);
                ArrayList<Recipe> recipes = RecipeActions.getAllRecipeItems(this);
                for (Recipe recipe: recipes) {
                    if (id == recipe.getId()) {
                        mRecipe = recipe;
                        break;
                    }
                }
            }

            if (mRecipe != null) {
                setTitle(mRecipe.getName());
                setFragments();
            }
        }
    }

    private void setFragments() {
        FragmentManager manager = getSupportFragmentManager();
        if (findViewById(R.id.main) != null) {
            mDescriptionFragment = new FragmentSteps();
            mDescriptionFragment.setItems(mRecipe.getSteps());
            mDescriptionFragment.setClickListener(this);
            manager.beginTransaction()
                    .add(R.id.main, mDescriptionFragment)
                    .commit();
        }

        if (findViewById(R.id.content) != null && mIngredientsContent != null && mContent != null) {

            mPlayerFragment = new FragmentPlayer();
            mPlayerFragment.setItems(mRecipe.getSteps(), 0);
            manager.beginTransaction()
                    .add(R.id.content, mPlayerFragment)
                    .commit();

            if (mLastFragment == LastFragment.PLAYER) {
                mContent.setVisibility(View.VISIBLE);
                mIngredientsContent.setVisibility(View.GONE);
            }
        }

        if (findViewById(R.id.ingredients) != null && mIngredientsContent != null && mContent != null) {

            FragmentIngredients mIngredientsFragment = new FragmentIngredients();
            mIngredientsFragment.setItems(mRecipe.getIngredients());
            manager.beginTransaction()
                    .add(R.id.ingredients, mIngredientsFragment)
                    .commit();

            if (mLastFragment == LastFragment.INGREDIENTS) {
                mContent.setVisibility(View.GONE);
                mIngredientsContent.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(RecipeSteps step, int position) {
        int id = step.getId();
        Intent intent;

        if (id >= 0 && findViewById(R.id.content) != null) {
            mPlayerFragment.updateItemPosition(position);
            mIngredientsContent.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
            mLastFragment = LastFragment.PLAYER;
            if (mDescriptionFragment != null) mDescriptionFragment.updateCurrentPositionSelected(position);
            return;

        } else if (findViewById(R.id.ingredients) != null) {
            mIngredientsContent.setVisibility(View.VISIBLE);
            mContent.setVisibility(View.GONE);
            mLastFragment = LastFragment.INGREDIENTS;
            if (mDescriptionFragment != null) mDescriptionFragment.updateCurrentPositionSelected(position);
            return;

        } else if (id >= 0) {
            intent = new Intent(this, StepsActivity.class);
        } else {
            intent = new Intent(this, IngredientsActivity.class);
        }

        intent.putParcelableArrayListExtra(INTENT_RECIPE_STEPS, mRecipe.getSteps());
        intent.putExtra(INTENT_RECIPE, mRecipe);
        intent.putExtra(INTENT_RECIPE_POSITION, position);
        startActivity(intent);
    }
}
