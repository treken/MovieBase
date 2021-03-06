package tobiapplications.com.moviebase.ui.viewholder.detail.movie;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import tobiapplications.com.moviebase.databinding.DetailActorsHolderBinding;
import tobiapplications.com.moviebase.listener.OnImageClickListener;
import tobiapplications.com.moviebase.model.detail.Actor;
import tobiapplications.com.moviebase.ui.views.ActorsPosterView;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ActorsViewHolder extends RecyclerView.ViewHolder implements OnImageClickListener {

    private DetailActorsHolderBinding bind;
    private Context context;
    private ArrayList<Actor> actors;

    public ActorsViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        bind = DetailActorsHolderBinding.bind(itemView);
    }

    public void setActorInformation(ArrayList<Actor> actors) {
        this.actors = actors;
        if (actors != null && !actors.isEmpty()) {
            for (int i = 0; i < actors.size(); i++) {
                ActorsPosterView actorsPosterView = new ActorsPosterView(context);
                bind.actorsGridLayout.addView(actorsPosterView);
                actorsPosterView.setActorInformation(actors.get(i), i, this);
            }
        }
    }

    @Override
    public void onImageClick(int position) {
        handleProfilImageClick(position);
    }

    private void handleProfilImageClick(int currentActor) {
        if (TextUtils.isEmpty(actors.get(currentActor).getProfilePath())) {
            return;
        }

        int howMuchWithImage = getHowMuchActorsWithImages();
        boolean fullListWithAndWithOutPictures[] = new boolean[actors.size()];

        int howMuchWithOutImage = 0;
        String[] actorImages = new String[howMuchWithImage];
        for (int i = 0; i < actors.size(); i++) {
            String movieImagePath = actors.get(i).getProfilePath();
            if (!TextUtils.isEmpty(movieImagePath)) {
                movieImagePath = NetworkUtils.getFullImageUrlHigh(movieImagePath);
                actorImages[i - howMuchWithOutImage] = movieImagePath;
                fullListWithAndWithOutPictures[i] = true;
            } else {
                howMuchWithOutImage++;
                fullListWithAndWithOutPictures[i] = false;
            }
        }

        int goBackCounter = GeneralUtils.getGoBackCounter(currentActor, fullListWithAndWithOutPictures);
        int startPosition = currentActor - goBackCounter;
        startImagesSlideShow(actorImages, startPosition);
    }

    private void startImagesSlideShow(String[] actorImages, int startPosition) {
        new ImageViewer.Builder(context, actorImages)
                .setStartPosition(startPosition)
                .show();
    }

    private int getHowMuchActorsWithImages() {
        int howMuchWithImage = 0;

        for (int i = 0; i < actors.size(); i++) {
            if (!TextUtils.isEmpty(actors.get(i).getProfilePath())) {
                howMuchWithImage++;
            }
        }

        return howMuchWithImage;
    }
}
