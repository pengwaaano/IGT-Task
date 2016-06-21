package com.jacobgreenland.igttask;

import android.app.Application;

import com.jacobgreenland.igttask.injection.components.APIComponents;
import com.jacobgreenland.igttask.injection.components.DaggerAPIComponents;
import com.jacobgreenland.igttask.injection.components.DaggerNetComponent;
import com.jacobgreenland.igttask.injection.components.NetComponent;
import com.jacobgreenland.igttask.injection.modules.APIModule;
import com.jacobgreenland.igttask.injection.modules.AppModule;
import com.jacobgreenland.igttask.injection.modules.NetModule;
import com.jacobgreenland.igttask.utils.Constants;


/**
 * Created by Jacob on 14/06/16.
 */
public class MyApp extends Application {

    private NetComponent mNetComponent;
    private APIComponents mApiComponents;
    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(Constants.BASE_URL))
                .appModule(new AppModule(this))
                .build();

        mApiComponents = DaggerAPIComponents.builder()
                .netComponent(mNetComponent)
                .aPIModule(new APIModule())
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public APIComponents getApiComponent() {
        return mApiComponents;
    }

}
