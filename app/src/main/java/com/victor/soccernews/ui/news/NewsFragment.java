package com.victor.soccernews.ui.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.victor.soccernews.MainActivity;
import com.victor.soccernews.data.local.AppDatabase;
import com.victor.soccernews.databinding.FragmentNewsBinding;
import com.victor.soccernews.ui.adapter.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);


        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, favoritedNews -> {
                MainActivity activity = (MainActivity) getActivity();
                AsyncTask.execute(() -> {
                    if(activity != null){
                        activity.getDb().newsDAO().save(favoritedNews);
                    }

                });

            }));
        });
        newsViewModel.getState().observe(getViewLifecycleOwner(), state ->{
            switch (state){
                case DOING:
                    //TODO: Incluir swipe to refresh layout
                    break;
                case DONE:

                    break;
                case ERROR:
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}