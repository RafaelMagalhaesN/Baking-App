package rmagalhaes.com.baking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Rafael Magalhães on 28/02/18.
 */

public class Recipe implements Parcelable{
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private ArrayList<RecipeIngredients> ingredients;

    @SerializedName("steps")
    private ArrayList<RecipeSteps> steps;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String image;

    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(RecipeIngredients.CREATOR);
        steps = in.createTypedArrayList(RecipeSteps.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<RecipeIngredients> getIngredients() {
        return ingredients;
    }


    public ArrayList<RecipeSteps> getSteps() {
        return steps;
    }



    public int getServings() {
        return servings;
    }



    public String getImage() {
        return image;
    }


}
