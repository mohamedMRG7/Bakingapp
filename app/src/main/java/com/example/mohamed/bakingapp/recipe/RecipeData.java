package com.example.mohamed.bakingapp.recipe;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;

/**
 * Created by mohamed on 4/18/18.
 */

public class RecipeData implements Parcelable {

    private String name;
    private String image;
    private String servings;

    private String quantity;
    private String mesure;
    private String ingrediant;

    private String shortDescrption;
    private String decription;
    private String videoUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMesure() {
        return mesure;
    }

    public void setMesure(String mesure) {
        this.mesure = mesure;
    }

    public String getIngrediant() {
        return ingrediant;
    }

    public void setIngrediant(String ingrediant) {
        this.ingrediant = ingrediant;
    }

    public String getShortDescrption() {
        return shortDescrption;
    }

    public void setShortDescrption(String shortDescrption) {
        this.shortDescrption = shortDescrption;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.servings);
        dest.writeString(this.quantity);
        dest.writeString(this.mesure);
        dest.writeString(this.ingrediant);
        dest.writeString(this.shortDescrption);
        dest.writeString(this.decription);
        dest.writeString(this.videoUrl);
    }

    public RecipeData() {
    }

    protected RecipeData(Parcel in) {
        this.name = in.readString();
        this.image = in.readString();
        this.servings = in.readString();
        this.quantity = in.readString();
        this.mesure = in.readString();
        this.ingrediant = in.readString();
        this.shortDescrption = in.readString();
        this.decription = in.readString();
        this.videoUrl = in.readString();
    }

    public static final Parcelable.Creator<RecipeData> CREATOR = new Parcelable.Creator<RecipeData>() {
        @Override
        public RecipeData createFromParcel(Parcel source) {
            return new RecipeData(source);
        }

        @Override
        public RecipeData[] newArray(int size) {
            return new RecipeData[size];
        }
    };
}
