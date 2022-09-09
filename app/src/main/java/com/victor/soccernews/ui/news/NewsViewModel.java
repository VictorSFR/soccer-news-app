package com.victor.soccernews.ui.news;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.victor.soccernews.data.SoccerNewsRepository;
import com.victor.soccernews.data.local.AppDatabase;
import com.victor.soccernews.domain.News;
import com.victor.soccernews.domain.remote.SoccerNewsAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {
    public enum State {
        DOING, DONE, ERROR;
    }
    private final MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();



    public NewsViewModel() {


        this.findNews();

    }

    public void findNews() {
        state.setValue(State.DOING);
        SoccerNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()){
                    mNews.setValue(response.body());
                    state.setValue(State.DONE);
                } else{
                    //TODO tratamento de erros
                    state.setValue(State.ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable error) {
                //TODO tratamento de erros
                error.printStackTrace();
                state.setValue(State.ERROR);
            }
        });
    }
    public void saveNews(News news){
        AsyncTask.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDAO().save(news));

    }

    public LiveData<List<News>> getNews() {
        return mNews;
    }
    public LiveData<State> getState() {
        return this.state;
    }

}