package com.justincamp.demo.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.justincamp.demo.R;
import com.justincamp.demo.api.RestClient;
import com.justincamp.demo.injection.ActivityComponent;
import com.justincamp.demo.injection.ActivityModule;
import com.justincamp.demo.injection.DaggerActivityComponent;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        } else {
            LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        }
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        ActivityModule module = new ActivityModule(this);
        activityComponent = DaggerActivityComponent.builder().activityModule(module).build();
        module.setActivityComponent(activityComponent);
//        activityComponent = DaggerActivityComponent.builder().activityModule(module).build();
        ImagePipelineConfig pipelineConfig = OkHttpImagePipelineConfigFactory.newBuilder(this, new RestClient().getClient()).build();
        Fresco.initialize(this, pipelineConfig);
        setContentView(R.layout.activity_main);
        NavController controller = Navigation.findNavController(this, R.id.navHost);
        NavigationUI.setupActionBarWithNavController(this, controller);
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.navHost).navigateUp();
    }

//    @Module
//    public class ActivityModule {
//
//        @Provides
//        @Singleton
//        public SharedPrefs providesSharedPrefs() {
//            return new SharedPrefs(MainActivity.this);
//        }
//
//        @Provides
//        @Singleton
//        public Discogs providesDiscogs() {
//            return new RestClient().getDiscogsService();
//        }
//
//        @Provides
//        @Singleton
//        public DiscogsAPI providesDiscogsAPI() {
//            DiscogsAPI api = new DiscogsAPI();
//            activityComponent.inject(api);
//            return api;
//        }
//
//    }

}
