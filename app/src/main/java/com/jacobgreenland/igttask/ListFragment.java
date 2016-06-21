package com.jacobgreenland.igttask;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacobgreenland.igttask.adapter.GameAdapter;
import com.jacobgreenland.igttask.game.GameContract;
import com.jacobgreenland.igttask.game.GamePresenter;
import com.jacobgreenland.igttask.model.Data;

import java.util.List;

/**
 * Created by Jacob on 21/06/16.
 */
public class ListFragment extends Fragment implements GameContract.View {

    View v;
    RecyclerView rv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    GameContract.Presenter fPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.game_recyclerview,container,false);
        //fPresenter = new PopPresenter(this);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv = (RecyclerView) v.findViewById(R.id.songList);

        //header = (RecyclerViewHeader) v.findViewById(R.id.header);


        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isOnline)
                    fPresenter.loadGames(MainActivity._api, false);
                else {
                    //Snackbar.make(v.findViewById(R.id.snackbarPosition2), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        //header.attachTo(rv);

        fPresenter = new GamePresenter(MainActivity.gameRepository,this);
        //fPresenter.loadGames(MainActivity._api, false);
        fPresenter.loadLocalGames();
    }

    public void loadGames()
    {
        fPresenter = new GamePresenter(MainActivity.gameRepository,this);
        fPresenter.loadGames(MainActivity._api, true);
    }

    @Override
    public void showDialog()
    {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showUsers(String s) {

    }

    @Override
    public void setAdapters(List<Data> results, boolean fromAPI)
    {
        //Log.d("test", results.getResults().get(0).getTrackName());
        GameAdapter mAdapterClassic = new GameAdapter(results, R.layout.game_row,  v.getContext());
        rv.setAdapter(mAdapterClassic);
        if(fromAPI)
            fPresenter.getRepository().getLocalSource().addData(results);
    }

    @Override
    public Context getApplicationContext() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        fPresenter.start();
    }

    @Override
    public void setGamePresenter(GameContract.Presenter presenter) {
        fPresenter = presenter;
    }

}
