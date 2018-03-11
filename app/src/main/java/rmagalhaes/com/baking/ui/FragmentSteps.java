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
import rmagalhaes.com.baking.adapters.StepsAdapter;
import rmagalhaes.com.baking.models.RecipeSteps;

import static rmagalhaes.com.baking.utils.Constants.SAVED_INSTANCE_ITEM_SELECTED;
import static rmagalhaes.com.baking.utils.Constants.SAVED_INSTANCE_STEPS_LIST_STATE;
import static rmagalhaes.com.baking.utils.Constants.SAVED_INSTANCE_STEPS_STATE;

/**
 * Created by Rafael Magalhães on 28/02/18.
 */

public class FragmentSteps extends Fragment {

    private RecyclerView mRecyclerView;
    private StepsAdapter mAdapter;
    private static StepsAdapter.StepsClickListener mClickListener;
    private ArrayList<RecipeSteps> mSteps;
    private int position = 0;

    public void setClickListener(StepsAdapter.StepsClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setItems(ArrayList<RecipeSteps> mSteps) {
        this.mSteps = mSteps;
    }

    public void updateCurrentPositionSelected(int pos) {
        this.position = pos;
        if (mAdapter != null) mAdapter.setPosition(pos);
    }

    public FragmentSteps() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int spanCount = 1;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), spanCount);

        mRecyclerView = view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new StepsAdapter(mClickListener);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STEPS_LIST_STATE)) {
            Parcelable recyclerViewState = savedInstanceState.getParcelable(SAVED_INSTANCE_STEPS_LIST_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STEPS_STATE)) {
            mSteps = savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_STEPS_STATE);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_ITEM_SELECTED)) {
            position = savedInstanceState.getInt(SAVED_INSTANCE_ITEM_SELECTED);
        }

        loadItems();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_INSTANCE_STEPS_LIST_STATE, mRecyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(SAVED_INSTANCE_STEPS_STATE, mSteps);
        outState.putInt(SAVED_INSTANCE_ITEM_SELECTED, position);
    }

    private void loadItems() {
        if (mSteps != null && mSteps.size() > 0) {
            mAdapter.setItems(mSteps);
            mAdapter.setPosition(position);
        }
    }
}
