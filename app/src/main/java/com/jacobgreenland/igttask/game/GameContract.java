package com.jacobgreenland.igttask.game;

import android.content.Context;

import com.jacobgreenland.igttask.BasePresenter;
import com.jacobgreenland.igttask.BaseView;
import com.jacobgreenland.igttask.observables.IGameAPI;
import com.jacobgreenland.igttask.data.GameRepository;
import com.jacobgreenland.igttask.model.Data;

import java.util.List;

/**
 * Created by Jacob on 16/06/16.
 */
public interface GameContract {

    interface View extends BaseView<Presenter> {
        void showUsers(String s);
        void setAdapters(List<Data> results, boolean fromAPI);
        void showDialog();
        Context getApplicationContext();
    }

    interface Presenter extends BasePresenter {
        void loadUsers();
        void loadGames(IGameAPI _api, final boolean initialLoad);
        void loadLocalGames();
        GameRepository getRepository();
        void setRepository(GameRepository songRepo);
    }
}
