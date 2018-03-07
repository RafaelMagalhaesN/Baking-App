package rmagalhaes.com.baking.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.models.RecipeSteps;

import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE;
import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE_POSITION;
import static rmagalhaes.com.baking.utils.Contants.INTENT_RECIPE_STEPS;

public class StepsActivity extends AppCompatActivity {

    private ArrayList<RecipeSteps> mRecipeSteps;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(INTENT_RECIPE_STEPS)) {
                mRecipeSteps = intent.getParcelableArrayListExtra(INTENT_RECIPE_STEPS);
            }

            if (intent != null && intent.hasExtra(INTENT_RECIPE_POSITION)) {
                position = intent.getIntExtra(INTENT_RECIPE_POSITION, 0);
            }

            if (mRecipeSteps != null) {
                setTitle(mRecipeSteps.get(position).getShortDescription());
                setFragments();
            }
        }
    }

    private void setFragments() {
        FragmentPlayer mFragmentPlayer = new FragmentPlayer();
        mFragmentPlayer.setItems(mRecipeSteps, position);
        FragmentManager manager = getSupportFragmentManager();

        if (findViewById(R.id.main) != null) {
            manager.beginTransaction()
                    .add(R.id.main, mFragmentPlayer)
                    .commit();
        }
    }
}
