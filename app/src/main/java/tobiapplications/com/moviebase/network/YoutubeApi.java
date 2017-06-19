package tobiapplications.com.moviebase.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;

/**
 * Created by Tobias on 19.06.2017.
 */

public interface YoutubeApi {

    @GET("videos")
    Call<YtSingleTrailerResponse> requestSingleTrailer(@Query("id") String trailerKey);
}
