package tobiapplications.com.moviebase.model.detail.views;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.detail.Genre;

/**
 * Created by Tobias on 15.06.2017.
 */

public class AdditionalInfoView {

    private String originalTitle;
    private int budget;
    private int revenue;
    private ArrayList<Genre> genres;
    private String homepage;

    public AdditionalInfoView(String originalTitle, int budget, int revenue, ArrayList<Genre> genres, String homepage) {
        this.originalTitle = originalTitle;
        this.budget = budget;
        this.revenue = revenue;
        this.genres = genres;
        this.homepage = homepage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getBudget() {
        return budget;
    }

    public int getRevenue() {
        return revenue;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }
}
