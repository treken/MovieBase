package tobiapplications.com.moviebase.utils;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

/**
 * Created by Tobi-Laptop on 23.01.2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        connectWithStetho(this);
    }

    public static void connectWithStetho(Context context) {
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(context))
                        .build());
    }

}
