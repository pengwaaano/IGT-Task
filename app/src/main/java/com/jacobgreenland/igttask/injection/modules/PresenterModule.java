package com.jacobgreenland.igttask.injection.modules;

import com.jacobgreenland.igttask.game.GameContract;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 */
@Module
public class PresenterModule {

    private final GameContract.View mView;

    public PresenterModule(GameContract.View view) { mView = view; }

    @Provides
    GameContract.View provideTasksContractView()
    {
        return mView;
    }
}
