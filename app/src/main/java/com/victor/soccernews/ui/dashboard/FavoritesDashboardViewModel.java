package com.victor.soccernews.ui.dashboard;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.victor.soccernews.data.SoccerNewsRepository;
import com.victor.soccernews.domain.News;

import java.util.List;

public class FavoritesDashboardViewModel extends ViewModel {


    public FavoritesDashboardViewModel() {

    }

    public LiveData<List<News>> loadFavoriteNews() {
        final LiveData<List<News>> news;
        news = SoccerNewsRepository.getInstance().getLocalDb().newsDAO().loadFavoriteNews();
        return news;
    }

    public void saveNews(News news){
        AsyncTask.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDAO().save(news));

    }
}