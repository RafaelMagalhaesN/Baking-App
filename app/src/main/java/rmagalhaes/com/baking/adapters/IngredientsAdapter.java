package rmagalhaes.com.baking.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.models.RecipeIngredients;

/**
 * Created by Rafael Magalhães on 22/02/18.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private ArrayList<RecipeIngredients> mIngredients = new ArrayList<>();

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_ingredients;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        holder.setIngredient();
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public void setItems(ArrayList<RecipeIngredients> ingredients) {
        this.mIngredients = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        final TextView mIngredient;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            mIngredient = itemView.findViewById(R.id.ingredient_name);
        }

        void setIngredient() {
            int adapterPosition = getAdapterPosition();
            if (mIngredients != null && mIngredients.size() > 0) {
                String ingredient = mIngredients.get(adapterPosition).getIngredient();
                String measure = mIngredients.get(adapterPosition).getMeasure();
                String quantity = String.valueOf(mIngredients.get(adapterPosition).getQuantity());
                String word = quantity + " " + measure + " of " + ingredient;
                mIngredient.setText(word);
            }
        }
    }
}
