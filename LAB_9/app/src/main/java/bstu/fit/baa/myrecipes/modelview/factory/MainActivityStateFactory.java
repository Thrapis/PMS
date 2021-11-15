package bstu.fit.baa.myrecipes.modelview.factory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import bstu.fit.baa.myrecipes.modelview.MainActivityState;

public class MainActivityStateFactory implements ViewModelProvider.Factory {

    private Application mApplication;

    public MainActivityStateFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityState(mApplication);
    }
}
