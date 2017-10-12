package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.OverviewAdapter;
import tobiapplications.com.moviebase.databinding.FragmentOverviewBinding;
import tobiapplications.com.moviebase.ui.NavigationActivity;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 10.09.2017.
 */

public class OverviewFragment extends Fragment implements OverviewFragmentContract.View {

    private FragmentOverviewBinding bind;
    private OverviewAdapter adapter;
    private Context context;
    private int overviewType;
    private NavigationActivity activity;

    public static OverviewFragment newInstance(int overviewType) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.TYPE, overviewType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentOverviewBinding.inflate(inflater);
        context = bind.getRoot().getContext();
        overviewType = getOverViewType(getArguments());
        return bind.getRoot();
    }

    private int getOverViewType(Bundle arguments) {
        if (arguments == null) {
            return -1;
        }

        if (arguments.containsKey(Constants.TYPE)) {
            return arguments.getInt(Constants.TYPE);
        }

        return -1;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        activity = (NavigationActivity) getActivity();
        adapter = new OverviewAdapter(getChildFragmentManager(), context, overviewType);
        setupViewPager();
        bind.tabs.setupWithViewPager(bind.viewpager);
        setTitle();
        setNavigationSelected();
    }

    private void setNavigationSelected() {
        NavigationActivity navigationActivity = (NavigationActivity) getActivity();
        int menuId = overviewType == Constants.Type.MOVIES ? R.id.menu_movies : R.id.menu_series;
        navigationActivity.setMenuItemChecked(menuId);
    }

    private void setTitle() {
        String title;
        if (overviewType == Constants.Type.MOVIES) {
            title = getString(R.string.movie_title);
        } else {
            title = getString(R.string.series_title);
        }

        getActivity().setTitle(title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setupViewPager() {
        bind.viewpager.disableSwipe(false);
        bind.viewpager.setOffscreenPageLimit(3);
        bind.viewpager.setAdapter(adapter);
    }
}
