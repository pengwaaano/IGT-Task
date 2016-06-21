package com.jacobgreenland.igttask.injection.modules;


import com.jacobgreenland.igttask.observables.IGameAPI;
import com.jacobgreenland.igttask.injection.scope.UserScope;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class APIModule {


    @UserScope
    @Provides
    public IGameAPI providesItemsInterface(RestAdapter retrofit) {
        return retrofit.create(IGameAPI.class);
    }
}
