package com.jacobgreenland.igttask.game;

import android.util.Log;

import com.jacobgreenland.igttask.observables.IGameAPI;
import com.jacobgreenland.igttask.data.GameRepository;
import com.jacobgreenland.igttask.model.Games;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 16/06/16.
 */
public class GamePresenter implements GameContract.Presenter {

    private final GameRepository gameRepository;

    private final GameContract.View mView;

    private String s;

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    Games games;
    int i = 0;

    @Inject
    public GamePresenter(GameRepository gameRepo, GameContract.View view) {
        mView = view;
        gameRepository = gameRepo;

        mView.setGamePresenter(this);
    }

    @Override
    public GameRepository getRepository()
    {
        return gameRepository;
    }

    @Override
    public void setRepository(GameRepository gameRepo)
    {
        //this.songRepository = songRepo;
    }

    @Override
    public void loadUsers() {
        i++;
        s = "Users Loaded " + i;
        mView.showUsers(s);
    }

    @Override
    public void loadGames(IGameAPI _api, final boolean initialLoad)
    {
        gameRepository.getRemoteSource().getGames(_api, initialLoad, mView,gameRepository);
    }
    @Override
    public void loadLocalGames()
    {
        Log.d("test", "local data loading");
        mView.setAdapters(gameRepository.getLocalSource().getDataFromLocal(),false);
    }

    @Inject
    @Override
    public void start() { }
}
