package com.example.netlab.todotest.Accessors.remote;

import android.util.Log;
import com.example.netlab.todotest.Accessors.LoginAccessor;
import com.example.netlab.todotest.LoginItem;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

/**
 * Created by Kolpa on 24.05.2017 use at own risk might be horribly broken...
 */
public class ResteasyLoginItemAccessorImpl implements LoginAccessor {
    protected static String logger = ResteasyLoginItemAccessorImpl.class.getSimpleName();


    private LoginAccessor accessor;

    public ResteasyLoginItemAccessorImpl(String baseUrl) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
        HttpClient client = new DefaultHttpClient(httpParams);


        this.accessor = ProxyFactory.create(LoginAccessor.class,
                baseUrl,
                new ApacheHttpClient4Executor(client));
    }
    @Override
    public boolean login(LoginItem loginItem) throws Exception {
        Log.i(logger, "login(): " + loginItem);
        return accessor.login(loginItem);
    }
}