package tobiapplications.com.moviebase.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.callbacks.ActorsCallback;
import tobiapplications.com.moviebase.network.callbacks.DetailMovieCallback;
import tobiapplications.com.moviebase.network.callbacks.DetailSerieCallback;
import tobiapplications.com.moviebase.network.callbacks.OverviewCallback;
import tobiapplications.com.moviebase.network.callbacks.ReviewCallback;
import tobiapplications.com.moviebase.network.callbacks.TrailersCallback;
import tobiapplications.com.moviebase.network.callbacks.YtTrailerCallback;
import tobiapplications.com.moviebase.ui.detail.DetailActivityPresenter;
import tobiapplications.com.moviebase.ui.detail.DetailFragmentContract;
import tobiapplications.com.moviebase.ui.search.SearchFragmentPresenter;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 10.06.2017.
 */

public class DataManager {

    private TheMovieApi movieApi;
    private YoutubeApi youtubeApi;
    private static DataManager dataManager;

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }

        return dataManager;
    }

    public void buildApi(String apiKey) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new QueryAppendingInterceptor(apiKey))
                .addInterceptor(LoggingInterceptor.getLogger())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.getApiBaseUrl(apiKey))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        if (apiKey.equals(Constants.THE_MOVIE_DB)) {
            movieApi = retrofit.create(TheMovieApi.class);
        } else if (apiKey.equals(Constants.YOUTUBE)) {
            youtubeApi = retrofit.create(YoutubeApi.class);
        }
    }

    public void requestPopularMovies(OnOverviewMovieLoadListener listener, int pageToLoad) {
        Call<MovieOverviewResponse> popularMovieCall = movieApi.requestPopularMovies(pageToLoad);
        popularMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestPopularSeries(OnOverviewMovieLoadListener listener, int pageToLoad) {
        Call<MovieOverviewResponse> popularSeriesCall = movieApi.requestPopularSeries(pageToLoad);
        popularSeriesCall.enqueue(new OverviewCallback(listener));
    }

    public void requestTopRatedMovies(OnOverviewMovieLoadListener listener, int pageToLoad) {
        Call<MovieOverviewResponse> topRatedMovieCall = movieApi.requestTopRatedMovies(pageToLoad);
        topRatedMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestTopRatedSeries(OnOverviewMovieLoadListener listener, int pageToLoad) {
        Call<MovieOverviewResponse> topRatedSeriesCall = movieApi.requestTopRatedSeries(pageToLoad);
        topRatedSeriesCall.enqueue(new OverviewCallback(listener));
    }

    public void requestSingleMovie(DetailActivityPresenter presenter, int movieId) {
        Call<MovieDetailResponse> singleMovieCall = movieApi.requestMovieDetails(movieId);
        singleMovieCall.enqueue(new DetailMovieCallback(presenter));
    }

    public void requestSingleSerie(DetailActivityPresenter presenter, int serieId) {
        Call<SeriesDetailResponse> singleSerieCall = movieApi.requestSeriesDetails(serieId);
        singleSerieCall.enqueue(new DetailSerieCallback(presenter));
    }

    public void requestSimilarMovies(OnOverviewMovieLoadListener listener, int movieId) {
        Call<MovieOverviewResponse> similarMovieCall = movieApi.requestSimilarMovies(movieId);
        similarMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestSimilarSeries(OnOverviewMovieLoadListener listener, int movieId) {
        Call<MovieOverviewResponse> similarSerieCall = movieApi.requestSimilarSeries(movieId);
        similarSerieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestReviews(DetailFragmentContract.Presenter presenter, int movieId) {
        Call<ReviewResponse> reviewResponseCall = movieApi.requestMovieReviews(movieId);
        reviewResponseCall.enqueue(new ReviewCallback(presenter));
    }

    public void requestSearchMovie(OnOverviewMovieLoadListener listener, String query) {
        Call<MovieOverviewResponse> searchMovieCall = movieApi.requestSearchMovie(query);
        searchMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestSearchSerie(OnOverviewMovieLoadListener listener, String query) {
        Call<MovieOverviewResponse> searchSerieCall = movieApi.requestSearchSerie(query);
        searchSerieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestActors(DetailFragmentContract.Presenter presenter, int movieId) {
        Call<ActorsResponse> actorsResponseCall = movieApi.requestActors(movieId);
        actorsResponseCall.enqueue(new ActorsCallback(presenter));
    }

    public void requestMovieTrailers(DetailFragmentContract.Presenter presenter, int movieId) {
        Call<TrailersResponse> trailerResponseCall = movieApi.requestMovieTrailers(movieId);
        trailerResponseCall.enqueue(new TrailersCallback(presenter));
    }

    public void requestSerieTrailer(DetailFragmentContract.Presenter presenter, int serieId) {
        Call<TrailersResponse> trailersResponseCall = movieApi.requestSerieTrailers(serieId);
        trailersResponseCall.enqueue(new TrailersCallback(presenter));
    }

    public void requestYoutubeTrailerInformation(DetailFragmentContract.Presenter presenter, String trailerKey) {
        Call<YtSingleTrailerResponse> youtubeSingleTrailerResponseCall = youtubeApi.requestSingleTrailer(trailerKey);
        youtubeSingleTrailerResponseCall.enqueue(new YtTrailerCallback(presenter, trailerKey));
    }

}
