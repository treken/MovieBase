package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.MovieOverviewModel;
import tobiapplications.com.moviebase.model.RecyclerItem;

/**
 * Created by Tobias on 09.06.2017.
 */

public class MovieOverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements PopupMenu.OnMenuItemClickListener {

    private final RecyclerView mRecyclerView;
    private final Context mContext;
    private ArrayList<RecyclerItem> itemList;
    private OnLoadMoreMoviesListener movieLoadListener;
    private OnMovieClickListener movieClickListener;
    private boolean triggerLoadMoreMovies = true;
    public static final int VIEW_TYPE_MOVIE = 100;
    public static final int VIEW_TYPE_LOADING = 101;
    private final GridLayoutManager mGridLayoutManager;


    public MovieOverviewAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        this.itemList = new ArrayList<>();
        setRecyclerViewScrollListener();
        mGridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
    }

    private void setRecyclerViewScrollListener() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isListEndReached()) {
                    if (triggerLoadMoreMovies) {
                        Log.d("MovieAdapter", "load next movies");
                        loadMoreMovies();
                    }
                }
            }
        });
    }

    private void loadMoreMovies() {
        triggerLoadMoreMovies = false;
        itemList.add(new RecyclerItem(VIEW_TYPE_LOADING, null));
        notifyItemInserted(itemList.size() - 1);
        movieLoadListener.loadMoreMovies();
    }

    private boolean isListEndReached() {
        int visibleItemCount = mGridLayoutManager.getChildCount();
        int totalItemCount = mGridLayoutManager.getItemCount();
        int pastVisibleItems = mGridLayoutManager.findFirstVisibleItemPosition();

        return pastVisibleItems + visibleItemCount >= totalItemCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MOVIE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
            return new MovieHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.movie_loading_item, parent, false);
            return new LoadingHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerItem item = itemList.get(position);
        if (item.getItemType() == VIEW_TYPE_MOVIE) {
            bindMovieItem(holder, item);
        } else if (item.getItemType() == VIEW_TYPE_LOADING) {
            bindLoadingItem(holder);
        }
    }

    private void bindLoadingItem(RecyclerView.ViewHolder holder) {
        LoadingHolder loadingHolder = (LoadingHolder) holder;

    }

    private void bindMovieItem(RecyclerView.ViewHolder holder, RecyclerItem item) {
        MovieHolder movieHolder = (MovieHolder) holder;
        MovieOverviewModel movie = (MovieOverviewModel) item.getItem();
        movieHolder.mMovieTitleNoPicture.setText(movie.getTitle());
        movieHolder.mMovieTitle.setText(movie.getTitle());
        Picasso.with(mContext).load(movie.getTitleImagePath()).into(movieHolder.mPosterImage);
        movieHolder.mMovieCardDots.setOnClickListener((View v) -> showPopMenu(v));
     }

    private void showPopMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_movie_card, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        if (itemList != null && !itemList.isEmpty()) {
            return itemList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList != null && !itemList.isEmpty()) {
            int type = itemList.get(position).getItemType();
            return type;
        }

        return -1;
    }

    public void setMovies(ArrayList<MovieOverviewModel> movies) {
        for (MovieOverviewModel movie : movies) {
            RecyclerItem item = new RecyclerItem(VIEW_TYPE_MOVIE, movie);
            itemList.add(item);
        }

        triggerLoadMoreMovies = true;

        notifyItemRangeChanged(itemList.size() - movies.size(), movies.size()); // TODO check, maybe size - 1
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add_favorite) {
            Toast.makeText(mContext, "Add favorite", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void setOnLoadMoreMoviesListener(OnLoadMoreMoviesListener loadListener) {
        this.movieLoadListener = loadListener;
    }

    public void setOnMovieClickListener(OnMovieClickListener clickListener) {
        this.movieClickListener = clickListener;
    }

    public void removeLoadingItem() {
        for (RecyclerItem item : itemList) {
            if (item.getItemType() == VIEW_TYPE_LOADING) {
                int index = itemList.indexOf(item);
                itemList.remove(index);
                notifyItemRangeChanged(index, itemList.size() - index); // TODO check, maybe size - index - 1
            }
        }
    }

    private class LoadingHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressLoading;

        public LoadingHolder(View itemView) {
            super(itemView);
            mProgressLoading = (ProgressBar) itemView.findViewById(R.id.movie_loading_progress_indicator);
        }
    }

    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mPosterImage;
        private TextView mMovieTitleNoPicture;
        private TextView mMovieTitle;
        private ImageView mMovieCardDots;

        public MovieHolder(View itemView) {
            super(itemView);

            mPosterImage = (ImageView) itemView.findViewById(R.id.movie_image);
            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            mMovieTitleNoPicture = (TextView) itemView.findViewById(R.id.movie_title_no_picture);
            mMovieCardDots = (ImageView) itemView.findViewById(R.id.movie_card_dots);
            mMovieCardDots.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (movieClickListener != null) {
                RecyclerItem item = itemList.get(getAdapterPosition());
                if (item.getItem() instanceof MovieOverviewModel) {
                    movieClickListener.onMovieClick(((MovieOverviewModel) item.getItem()).getId());
                }
            }
        }
    }
}
