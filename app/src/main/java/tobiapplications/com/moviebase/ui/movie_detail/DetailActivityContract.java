package tobiapplications.com.moviebase.ui.movie_detail;

import android.content.ContentValues;
import android.database.Cursor;

import tobiapplications.com.moviebase.model.detail_response.MovieDetailResponse;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailActivityContract {

    interface View {
        void setUpActionBar();
        void findMyViews();
        void getMovieId();
        void setUpTabFragment(MovieDetailResponse response);
        void setMovieInformation(String title, String moviePath);
        void onFabClickedToast(boolean marked);
        void insertMovieIntoDatabase(ContentValues values);
        void deleteCurrentMovieFromFavoriteDatabase(int movieId);
        Cursor getAllFavoriteMovies();
        void markFabAsFavorite(boolean isFavorite);
        void setFabButtonVisible();
    }

    interface Presenter {
        void requestSingleMovieDownload();
        void displayMovieResponse(MovieDetailResponse detailResponse);
        void displayError();
        void openToolbarImage();
        void handleFabClick();
        void insertCurrentMovieToFavoriteDatabase();
        void checkIfMovieIsMarkedAsFavorite();
    }

}
