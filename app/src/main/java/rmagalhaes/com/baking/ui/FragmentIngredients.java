package rmagalhaes.com.baking.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.adapters.IngredientsAdapter;
import rmagalhaes.com.baking.models.RecipeIngredients;

import static rmagalhaes.com.baking.utils.Constants.SAVED_INSTANCE_STEPS_LIST_STATE;

/**
 * Created by Rafael Magalhães on 03/03/18.
 */

public class FragmentIngredients extends Fragment {


    private IngredientsAdapter mAdapter;
    private static ArrayList<RecipeIngredients> mIngredients = new ArrayList<>();


    public void setItems(ArrayList<RecipeIngredients> ingredients) {
        mIngredients = ingredients;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int spanCount = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), spanCount);

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new IngredientsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STEPS_LIST_STATE)) {
            Parcelable recyclerViewState = savedInstanceState.getParcelable(SAVED_INSTANCE_STEPS_LIST_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }

        loadItems();
    }

    private void loadItems() {
        if (mIngredients != null && mIngredients.size() > 0) {
            mAdapter.setItems(mIngredients);
        }
    }
}
