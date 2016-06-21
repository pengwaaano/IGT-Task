package com.jacobgreenland.igttask.injection.components;

import com.jacobgreenland.igttask.injection.modules.APIModule;
import com.jacobgreenland.igttask.injection.scope.UserScope;

import dagger.Component;


/**
 * Created by kalpesh on 20/01/2016.
 */

    @UserScope
    @Component(dependencies =NetComponent.class, modules = APIModule.class)
    public interface APIComponents {

    void inject(com.jacobgreenland.igttask.MainActivity activity);
}
