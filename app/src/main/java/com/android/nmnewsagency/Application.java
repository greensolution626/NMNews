package com.android.nmnewsagency;

import android.content.Context;

import com.android.nmnewsagency.pref.Prefrence;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

public class Application extends android.app.Application {
    private HttpProxyCacheServer proxy;
    @Override
    public void onCreate() {
        super.onCreate();

        Prefrence.init(this);
        // init with context, you can do this in application or activity.

        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
    public static HttpProxyCacheServer getProxy(Context context) {
        Application app = (Application) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {

        //return new HttpProxyCacheServer(this);
        return new HttpProxyCacheServer.Builder(this)

                .maxCacheFilesCount(40)
                .maxCacheSize(1024 * 1024 * 1024)
                .build();
    }
}
