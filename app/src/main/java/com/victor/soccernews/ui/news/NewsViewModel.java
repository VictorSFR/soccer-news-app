package com.victor.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.victor.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> mNews;

    public NewsViewModel() {

        //TODO remover mock de noticias
        mNews = new MutableLiveData<>();
        List<News> news = new ArrayList<>();
        news.add(new News("Flamengo derrota Palmeiras", "Descrição do negócio"));
        news.add(new News("Botafogo derrota Vasco", "Descrição do negócio"));

        this.mNews.setValue(news);
    }

    public LiveData<List<News>> getNews() {
        return mNews;
    }
}