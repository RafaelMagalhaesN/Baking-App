package rmagalhaes.com.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rafael Magalhães on 28/02/18.
 */

public class RecipeIngredients implements Parcelable{

    @SerializedName("quantity")
    private double quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    protected RecipeIngredients(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeIngredients> CREATOR = new Creator<RecipeIngredients>() {
        @Override
        public RecipeIngredients createFromParcel(Parcel in) {
            return new RecipeIngredients(in);
        }

        @Override
        public RecipeIngredients[] newArray(int size) {
            return new RecipeIngredients[size];
        }
    };

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
