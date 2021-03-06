package tobiapplications.com.moviebase.adapter.delegates;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.SummaryItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.SummaryViewHolder;

/**
 * Created by Tobias on 06.09.2017.
 */

public class SummaryViewDelegate extends AdapterDelegate<List<DisplayableItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SummaryItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SummaryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_summary_holder, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SummaryItem summaryItem = (SummaryItem) items.get(position);
        SummaryViewHolder summaryViewHolder = (SummaryViewHolder) holder;
        summaryViewHolder.setSummary(summaryItem.getSummary());
    }
}