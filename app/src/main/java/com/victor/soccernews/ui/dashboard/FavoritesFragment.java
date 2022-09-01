package com.victor.soccernews.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.victor.soccernews.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesDashboardViewModel favoritesDashboardViewModel =
                new ViewModelProvider(this).get(FavoritesDashboardViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        
        favoritesDashboardViewModel.getText().observe(getViewLifecycleOwner(), binding.textDashboard::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}