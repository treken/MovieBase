package tobiapplications.com.moviebase.ui.detail;

import android.view.MenuItem;

import com.google.android.material.appbar.AppBarLayout;

import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.utils.mvp.BaseMvpPresenter;
import tobiapplications.com.moviebase.utils.mvp.BaseView;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailActivityContract {

    interface View extends BaseView, AppBarLayout.OnOffsetChangedListener{
        void setUpActionBar();
        void setUpMovieTabFragment(MovieDetailResponse response, int overviewType);
        void setUpSeriesTabFragment(SeriesDetailResponse clickedSerie, int overviewType);
        void displayTitle(String title);
        void markFabAsFavorite();
        void unMarkFabFromFavorite();
        void setFabButtonVisible();
        void animateFabDown();
        void animateFabUp();
        void showMarkAsFavorite(String movieTitle);
        void showRemovedFromFavorite(String movieTitle);
        void showNoPictureAvailable(boolean noPicture);
        void showPosterImage(String imageUrl);
    }

    interface Presenter extends BaseMvpPresenter<View> {
        boolean hasInternetConnection();
        void openToolbarImage();
        void handleFabClick();
        void insertIntoDatabase(int overviewType);
        void setFabDependingOnFavoriteStatus();
        void setAppBarOffsetChanged(int totalScrollRange, int verticalOffset);
        void onMenuItemClicked(MenuItem menuItem);
    }

}
