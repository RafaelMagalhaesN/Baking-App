package rmagalhaes.com.baking.data.helpers;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Rafael Magalhães on 04/03/18.
 */

public class RecipeContract {
    public static final String AUTHORITY = "rmagalhaes.com.baking";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_RECIPES = "recipes";
    public static final long INVALID_RECIPE_ID = -1;

    public static final class RecipeEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String TABLE_NAME = "plants";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_RECIPE_NAME = "recipe_name";
        public static final String COLUMN_RECIPE_SERVINGS = "servings";
    }
}
