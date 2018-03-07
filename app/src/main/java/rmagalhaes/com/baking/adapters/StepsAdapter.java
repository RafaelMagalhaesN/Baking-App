package rmagalhaes.com.baking.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.models.RecipeSteps;

/**
 * Created by Rafael Magalhães on 22/02/18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private ArrayList<RecipeSteps> mSteps = new ArrayList<>();
    private int position = 0;

    private final StepsClickListener mClickListener;


    public StepsAdapter(StepsClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface StepsClickListener {
        void onClick(RecipeSteps step, int position);
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_steps;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.setStep();
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public void setItems(ArrayList<RecipeSteps> steps) {
        if (this.mSteps != null) this.mSteps.clear();
        this.mSteps = steps;
        notifyDataSetChanged();
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mStepInstruction;
        final RelativeLayout cardBg;

        public StepsViewHolder(View itemView) {
            super(itemView);
            cardBg = itemView.findViewById(R.id.cardBg);
            mStepInstruction = itemView.findViewById(R.id.step_item);
            itemView.setOnClickListener(this);
        }

        void setStep() {
            int adapterPosition = getAdapterPosition();
            if (mSteps != null && mSteps.size() > 0) {
                String description = mSteps.get(adapterPosition).getShortDescription();
                mStepInstruction.setText(description);
                cardBg.setBackgroundColor(Color.TRANSPARENT);
                if (position == adapterPosition) {
                    cardBg.setBackgroundColor(Color.GRAY);
                }
            }
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (mSteps != null && mSteps.size() > 0) {
                RecipeSteps step = mSteps.get(adapterPosition);
                mClickListener.onClick(step, adapterPosition);
            }

        }
    }
}
