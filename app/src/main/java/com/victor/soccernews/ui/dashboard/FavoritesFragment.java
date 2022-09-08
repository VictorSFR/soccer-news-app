package com.victor.soccernews.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.victor.soccernews.MainActivity;
import com.victor.soccernews.databinding.FragmentFavoritesBinding;
import com.victor.soccernews.domain.News;
import com.victor.soccernews.ui.adapter.NewsAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesDashboardViewModel favoritesDashboardViewModel =
                new ViewModelProvider(this).get(FavoritesDashboardViewModel.class);
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);


        loadFavoriteNews();
        return binding.getRoot();
    }

    private void loadFavoriteNews() {
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
        List<News> favoriteNews = activity.getDb().newsDAO().loadFavoriteNews();
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvNews.setAdapter(new NewsAdapter(favoriteNews, updatedNews -> {
            

                    activity.getDb().newsDAO().save(updatedNews);
                    loadFavoriteNews();
            

        }));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}