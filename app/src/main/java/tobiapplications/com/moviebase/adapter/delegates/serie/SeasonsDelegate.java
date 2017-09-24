package tobiapplications.com.moviebase.adapter.delegates.serie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.serie.SeasonsItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.serie.SeasonsHolder;

/**
 * Created by Tobias on 20.09.2017.
 */

public class SeasonsDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Context context;

    public SeasonsDelegate(Context context) {
    this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SeasonsItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SeasonsHolder(LayoutInflater.from(context).inflate(R.layout.detail_seasons_holder, parent, false), context);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SeasonsHolder seasonsHolder = (SeasonsHolder) holder;
        SeasonsItem seasonsItem = (SeasonsItem) items.get(position);
        seasonsHolder.setSeasons(seasonsItem.getSeasons());
    }
}
