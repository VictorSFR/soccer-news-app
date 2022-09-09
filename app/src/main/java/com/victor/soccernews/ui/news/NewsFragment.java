package com.victor.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
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
        observe(newsViewModel);
        observeStates(newsViewModel);
        binding.srlMatches.setOnRefreshListener(newsViewModel::findNews);
        return root;
    }

    private void observeStates(NewsViewModel newsViewModel) {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state ->{
            switch (state){
                case DOING:
                    binding.srlMatches.setRefreshing(true);
                    break;
                case DONE:
                    binding.srlMatches.setRefreshing(false);
                    break;
                case ERROR:
                    binding.srlMatches.setRefreshing(false);
                    Snackbar.make(binding.srlMatches, "Network error.", Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    private void observe(NewsViewModel newsViewModel) {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, newsViewModel::saveNews));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}