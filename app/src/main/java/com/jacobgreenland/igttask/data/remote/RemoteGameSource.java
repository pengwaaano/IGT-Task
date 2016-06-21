package com.jacobgreenland.igttask.data.remote;

import android.util.Log;

import com.jacobgreenland.igttask.MainActivity;
import com.jacobgreenland.igttask.game.GameContract;
import com.jacobgreenland.igttask.observables.IGameAPI;
import com.jacobgreenland.igttask.data.GameRepository;
import com.jacobgreenland.igttask.model.Games;

import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jacob on 17/06/16.
 */
public class RemoteGameSource {

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    Games games;

    public RemoteGameSource()
    {

    }

    public void getGames(IGameAPI _api, final boolean initialLoad, final GameContract.View mView, final GameRepository gameRepository)
    {
        _subscriptions.add(_api.getGames()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<Games>()
                {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "Error");
                    }
                    @Override
                    public void onCompleted()
                    {
                        Log.i("Retrofit", "onCompleted");
                        MainActivity.loadedGame = games;
                        if(initialLoad)
                        {
                            gameRepository.getLocalSource().addData(games.getData());
                            mView.setAdapters(games.getData(), true);
                        }
                        else
                        {
                            mView.setAdapters(games.getData(), true);
                            mView.showDialog();
                        }
                    }
                    @Override
                    public void onNext(Games results2) {
                        Log.i("Retrofit", "onNext");

                        games = results2;
                    }
                }));
    }
}
