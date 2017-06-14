package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 15.06.2017.
 */

public class InfoViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ImageView infoImage;
    private RatingBar infoRatingBar;
    private TextView infoRatingAverage;
    private TextView infoRatingCount;

    public InfoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        infoImage = (ImageView) itemView.findViewById(R.id.info_image);
        infoRatingBar = (RatingBar) itemView.findViewById(R.id.info_rating_bar);
        infoRatingAverage = (TextView) itemView.findViewById(R.id.info_rating_average);
        infoRatingCount = (TextView) itemView.findViewById(R.id.info_rating_count);
    }


    public void setInformation(String imagePath, double average, int count) {
        Picasso.with(context).load(imagePath).into(infoImage);
        infoRatingAverage.setText(String.valueOf(average));
        infoRatingCount.setText(count);
    }
}
