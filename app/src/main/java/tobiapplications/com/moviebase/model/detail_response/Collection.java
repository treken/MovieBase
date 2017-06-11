package tobiapplications.com.moviebase.model.detail_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tobias on 11.06.2017.
 */

public class Collection {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("poster_path")
    private String titleImagePath;
    @SerializedName("backdrop_path")
    private String backgroundImagePath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitleImagePath() {
        return titleImagePath;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }
}
