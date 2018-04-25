package com.example.mohamed.bakingapp.utilies;

import com.example.mohamed.bakingapp.recipe.RecipeData;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by mohamed on 4/17/18.
 */

public class JsonUtilise {

    private static final String NAME="name";
    private static final String BAKING_IMAGE="image";

    private static final String INGREDIANTS="ingredients";
    private static final String QUNTITY="quantity";
    private static final String MESURE="measure";
    private static final String INGREDANT="ingredient";

    private static final String STEPS="steps";
    private static final String SHORT_DECRIPTION="shortDescription";
    private static final String DECRIPTION="description";
    private static final String VIDEO_URL="videoURL";

    private static final String SERVINGS="servings";





    public static RecipeData getIngredients(String json, int recipeId, int ingrediantsId)
    {

        try {
            JSONArray recipes=new JSONArray(json);
            JSONArray ingredients=recipes.optJSONObject(recipeId).getJSONArray(INGREDIANTS);

            String measure=ingredients.optJSONObject(ingrediantsId).optString(MESURE);
            String quantity=ingredients.optJSONObject(ingrediantsId).optString(QUNTITY);
            String ingredient=ingredients.optJSONObject(ingrediantsId).optString(INGREDANT);

            RecipeData data=new RecipeData();
            data.setMesure(measure);
            data.setQuantity(quantity);
            data.setIngrediant(ingredient);

            return data;

        } catch (JSONException e) {

            return null;
        }


    }

    public static RecipeData getSteps(String json, int recipeId, int stepId)
    {

        try {
            JSONArray recipes=new JSONArray(json);
            JSONArray steps=recipes.optJSONObject(recipeId).optJSONArray(STEPS);

            String shortDescription=steps.optJSONObject(stepId).optString(SHORT_DECRIPTION);
            String description=steps.optJSONObject(stepId).optString(DECRIPTION);
            String videoUrl=steps.optJSONObject(stepId).optString(VIDEO_URL);
            String image=recipes.optJSONObject(recipeId).optString(BAKING_IMAGE);
            RecipeData data=new RecipeData();
            data.setShortDescrption(shortDescription);
            data.setDecription(description);
            data.setVideoUrl(videoUrl);
            data.setImage(image);

            return data;

        } catch (JSONException e) {

            return null;
        }


    }

    public static RecipeData getRecips(String json, int recipeId)
    {

        try {
            JSONArray recipes=new JSONArray(json);

            String name=recipes.optJSONObject(recipeId).optString(NAME);
            String image=recipes.optJSONObject(recipeId).optString(BAKING_IMAGE);
            RecipeData data=new RecipeData();
            data.setName(name);
            data.setImage(image);

            return data;

        } catch (JSONException e) {

            return null;
        }


    }


    public static int getRecipsCount(String json)
    {
        if (json !=null) {
            JSONArray recipes = null;
            try {
                recipes = new JSONArray(json);
            } catch (JSONException e) {

            }

            if (recipes != null)
                return recipes.length();
            else return 0;
        }else return 0;
    }


    public static int getIngredientsCount(String json, int recipeId)
    {
        if (json!=null) {
            JSONArray ingredients = null;
            try {
                JSONArray recips = new JSONArray(json);
                ingredients = recips.optJSONObject(recipeId).getJSONArray(INGREDIANTS);
            } catch (JSONException e) {

            }

            if (ingredients != null)
                return ingredients.length();
            else return 0;

        }else return 0;
    }

    public static int getStepsCount(String json,int recipeId)
    {
        if (json!=null) {
            JSONArray steps = null;
            try {
                JSONArray recips = new JSONArray(json);
                steps = recips.optJSONObject(recipeId).getJSONArray(STEPS);
            } catch (JSONException e) {

            }

            if (steps != null)
                return steps.length();
            else return 0;

        }else return 0;
    }
}
