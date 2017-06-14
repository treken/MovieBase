package tobiapplications.com.moviebase.ui.movie_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.DetailAdapter;
import tobiapplications.com.moviebase.model.detail_response.MovieDetailResponse;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 11.06.2017.
 */

public class DetailFragment extends Fragment implements DetailFragmentContract.View{

    private RecyclerView recyclerView;
    private DetailAdapter adapter;
    private DetailFragmentPresenter presenter;

    private Context mContext;
    private MovieDetailResponse detailMovie;

    public static DetailFragment newInstance(MovieDetailResponse response) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLICKED_MOVIE, response);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailMovie = (MovieDetailResponse) getArguments().get(Constants.CLICKED_MOVIE);
        adapter = new DetailAdapter(mContext);
        presenter = new DetailFragmentPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();

        findMyViews();
        setAdapter();
        presenter.buildUiFromResponse(detailMovie);
    }

    private void findMyViews() {
        if (getView() != null) {
          recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        }
    }

    public void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }








//    private void setMovieInformation() {
//        Picasso.with(mContext).load(NetworkUtils.getFullImageUrl(detailMovie.getTitleImagePath())).into(mDetailMovieTitleImage);
//
//        mDetailMovieYear.setText(DateUtils.getDMYFromYMD(detailMovie.getReleaseDate()));
//        mDetailMovieTitle.setText(detailMovie.getTitle());
//        mDetailMovieDescription.setText(detailMovie.getDescription());
//        rating.setRating((float) detailMovie.getVoteAverage());
//        ratingTextView.setText(String.valueOf(detailMovie.getVoteAverage()));
//        boolean adult = detailMovie.isAdult();
//        if (adult) {
//            mDetailMovieAdult.setText(getString(R.string.string_true));
//        } else {
//            mDetailMovieAdult.setText(getString(R.string.string_false));
//        }
//
//        if (getView() != null) {
//            FlexboxLayout mainGenreLayout = (FlexboxLayout) getView().findViewById(R.id.genreLayout);
//            ArrayList<Genre> genres = detailMovie.getGenres();
//            for (int i = 0; i < genres.size(); i++) {
//                String genre = genres.get(i).getName();
//
//                TextView tvGenre = (TextView) getActivity().getLayoutInflater().inflate(R.layout.textview_genres, null);
//                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                params.setMargins(0, 0 , 5, 5);
//                tvGenre.setLayoutParams(params);
//                tvGenre.setText(genre);
//
//                mainGenreLayout.addView(tvGenre);
//            }
//        }
//    }

    @Override
    public void onClick(View v) {

    }
}
