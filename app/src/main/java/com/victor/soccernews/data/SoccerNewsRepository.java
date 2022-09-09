package com.victor.soccernews.data;

import androidx.room.Room;

import com.victor.soccernews.App;
import com.victor.soccernews.data.local.AppDatabase;
import com.victor.soccernews.domain.remote.SoccerNewsAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepository {
    //region Constantes
    private static final String REMOTE_API_URL = "https://digitalinnovationone.github.io/soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";
    //endregion

    //region Atributos: encapsulam o acesso a nossa API (Retrofit) e banco de dados local (Room).
    private SoccerNewsAPI remoteApi;
    private AppDatabase localDb;

    public SoccerNewsAPI getRemoteApi() {
        return remoteApi;
    }

    public AppDatabase getLocalDb() {
        return localDb;
    }
    //endregion

    //region Singleton: garante uma instância única dos atributos relacionados ao Retrofit e Room.
    private SoccerNewsRepository () {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoccerNewsAPI.class);
        if (App.getInstance() == null){
            System.out.println("CONTEXTO ESTÁ NULO!");
        }
        localDb = Room.databaseBuilder(App.getInstance(), AppDatabase.class, LOCAL_DB_NAME).build();
    }

    public static SoccerNewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }
    //endregion
}
