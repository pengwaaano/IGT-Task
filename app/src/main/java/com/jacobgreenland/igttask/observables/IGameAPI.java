package com.jacobgreenland.igttask.observables;

import com.jacobgreenland.igttask.model.Games;
import com.jacobgreenland.igttask.utils.Constants;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Jacob on 21/06/16.
 */
public interface IGameAPI {
    @GET(Constants.SECOND_URL)
    Observable<Games> getGames();
}
