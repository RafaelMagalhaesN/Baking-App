package rmagalhaes.com.baking.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.data.RecipeActions;
import rmagalhaes.com.baking.models.Recipe;

/**
 * Created by Rafael Magalhães on 18/02/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    private ArrayList<Recipe> mRecipes = new ArrayList<>();
    private final RecipeClickListener mRecipeClickListener;

    public interface RecipeClickListener {
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(RecipeClickListener clickListener) {
        this.mRecipeClickListener = clickListener;
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.setImage();
        holder.setTexts();
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void setItems(ArrayList<Recipe> recipes) {
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mRecipeImage;
        final ImageView mRecipeFavorite;
        final TextView mRecipeName;
        final TextView mRecipeServings;
        final View view;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mRecipeServings = itemView.findViewById(R.id.servings);
            mRecipeName = itemView.findViewById(R.id.recipe_name);
            mRecipeImage = itemView.findViewById(R.id.recipe_image);
            mRecipeFavorite = itemView.findViewById(R.id.favorite);
            view.setOnClickListener(this);
            mRecipeFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (mRecipes != null) {
                        Recipe recipe = mRecipes.get(adapterPosition);
                        boolean isFavorite = RecipeActions.isFavorite(mRecipes.get(adapterPosition), view.getContext());
                        if (isFavorite) {
                            RecipeActions.removeFavoriteRecipe(recipe, view.getContext());
                            mRecipeFavorite.setImageResource(R.drawable.ic_heart_unselected);
                        } else {
                            RecipeActions.addFavoriteRecipe(recipe, view.getContext());
                            mRecipeFavorite.setImageResource(R.drawable.ic_heart);
                        }
                    }
                }
            });
        }

        void setImage() {
            int adapterPosition = getAdapterPosition();
            if (mRecipes != null) {
                Recipe recipe = mRecipes.get(adapterPosition);
                String imageUrl = recipe.getImage();
                Drawable drawable = ResourcesCompat.getDrawable(view.getResources(), R.drawable.baking_placeholder, null);
                if (drawable != null && !imageUrl.isEmpty()) {
                    Picasso.with(view.getContext()).load(imageUrl).error(drawable).into(mRecipeImage);
                }
                mRecipeFavorite.setImageResource(R.drawable.ic_heart_unselected);
                boolean isFavorite = RecipeActions.isFavorite(mRecipes.get(adapterPosition), view.getContext());
                if (isFavorite) {
                    mRecipeFavorite.setImageResource(R.drawable.ic_heart);
                }
            }
        }

        void setTexts() {
            int adapterPosition = getAdapterPosition();
            if (mRecipes != null) {
                Recipe recipe = mRecipes.get(adapterPosition);
                String title = recipe.getName();
                String subtitle = String.valueOf(recipe.getServings());
                mRecipeName.setText(title);
                mRecipeServings.setText(subtitle);
            }
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (mRecipes != null) {
                Recipe recipe = mRecipes.get(adapterPosition);
                mRecipeClickListener.onClick(recipe);
            }
        }
    }
}
