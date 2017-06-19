package tobiapplications.com.moviebase.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoad;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.callbacks.ActorsCallback;
import tobiapplications.com.moviebase.network.callbacks.DetailCallback;
import tobiapplications.com.moviebase.network.callbacks.OverviewCallback;
import tobiapplications.com.moviebase.network.callbacks.ReviewCallback;
import tobiapplications.com.moviebase.network.callbacks.TrailersCallback;
import tobiapplications.com.moviebase.network.callbacks.YtTrailerCallback;
import tobiapplications.com.moviebase.ui.detail.DetailActivityPresenter;
import tobiapplications.com.moviebase.ui.detail.DetailFragmentContract;
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

    public void buildMovieApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.getLogger())
                .addInterceptor(new QueryAppendingInterceptor(Constants.THE_MOVIE_DB))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.getMovieBaseUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieApi = retrofit.create(TheMovieApi.class);
    }

    public void buildYoutubeApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.getLogger())
                .addInterceptor(new QueryAppendingInterceptor(Constants.YOUTUBE))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.getYoutubeBaseUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        youtubeApi = retrofit.create(YoutubeApi.class);
    }

    public void requestPopularMovies(OnOverviewMovieLoad listener, int pageToLoad) {
        Call<MovieOverviewResponse> popularMovieCall = movieApi.requestPopularMovies(pageToLoad);
        popularMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestTopRatedMovies(OnOverviewMovieLoad listener, int pageToLoad) {
        Call<MovieOverviewResponse> topRatedMovieCall = movieApi.requestTopRatedMovies(pageToLoad);
        topRatedMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestSingleMovie(DetailActivityPresenter presenter, int movieId) {
        Call<MovieDetailResponse> singleMovieCall = movieApi.requestDetailInfo(movieId);
        singleMovieCall.enqueue(new DetailCallback(presenter));
    }

    public void requestSimilarMovies(OnOverviewMovieLoad listener, int movieId) {
        Call<MovieOverviewResponse> similarMovieCall = movieApi.requestSimilarMovies(movieId);
        similarMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestReviews(DetailFragmentContract.Presenter presenter, int movieId) {
        Call<ReviewResponse> reviewResponseCall = movieApi.requestMovieReviews(movieId);
        reviewResponseCall.enqueue(new ReviewCallback(presenter));
    }

    public void requestSearchMovie(OnOverviewMovieLoad listener, String query) {
        Call<MovieOverviewResponse> searchMovieCall = movieApi.requestSearchMovie(query);
        searchMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestActors(DetailFragmentContract.Presenter presenter, int movieId) {
        Call<ActorsResponse> actorsResponseCall = movieApi.requestActors(movieId);
        actorsResponseCall.enqueue(new ActorsCallback(presenter));
    }

    public void requestTrailers(DetailFragmentContract.Presenter presenter, int movieId) {
        Call<TrailersResponse> trailerResponseCall = movieApi.requestTrailers(movieId);
        trailerResponseCall.enqueue(new TrailersCallback(presenter));
    }

    public void requestYoutubeTrailerInformation(DetailFragmentContract.Presenter presenter, String trailerKey) {
        Call<YtSingleTrailerResponse> youtubeSingleTrailerResponseCall = youtubeApi.requestSingleTrailer(trailerKey);
        youtubeSingleTrailerResponseCall.enqueue(new YtTrailerCallback(presenter));
    }
}
