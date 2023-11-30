package com.daominh.quickmem.ui.activities.classes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daominh.quickmem.R;
import com.daominh.quickmem.adapter.SetsAdapter;
import com.daominh.quickmem.data.dao.FlashCardDAO;
import com.daominh.quickmem.data.model.FlashCard;
import com.daominh.quickmem.databinding.FragmentViewSetsBinding;
import com.daominh.quickmem.preferen.UserSharePreferences;
import com.daominh.quickmem.ui.activities.create.CreateSetActivity;

import java.util.ArrayList;


public class ViewSetsFragment extends Fragment {
    private FragmentViewSetsBinding binding;
    private UserSharePreferences userSharePreferences;
    private ArrayList<FlashCard> flashCards;
    private FlashCardDAO flashCardDAO;
    private SetsAdapter setsAdapter;
    private String idUser;
    private int count = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flashCardDAO = new FlashCardDAO(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewSetsBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userSharePreferences = new UserSharePreferences(requireActivity());
        idUser = userSharePreferences.getId();
        binding.addSetsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        flashCards = flashCardDAO.getAllFlashCardByUserId(idUser);

        if (flashCards.size() == 0) {
            binding.setsLl.setVisibility(View.VISIBLE);
            binding.setsRv.setVisibility(View.GONE);
        } else {
            binding.setsLl.setVisibility(View.GONE);
            binding.setsRv.setVisibility(View.VISIBLE);
        }
        count = flashCards.size();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        binding.setsRv.setLayoutManager(linearLayoutManager);
        setsAdapter = new SetsAdapter(requireActivity(), flashCards, true);
        binding.setsRv.setAdapter(setsAdapter);
        setsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }
    private void refreshData() {
        flashCards = flashCardDAO.getAllFlashCardByUserId(idUser);
        setsAdapter = new SetsAdapter(requireActivity(), flashCards, true);
        binding.setsRv.setAdapter(setsAdapter);
        setsAdapter.notifyDataSetChanged();

        if (flashCards.size() == 0) {
            binding.setsLl.setVisibility(View.VISIBLE);
            binding.setsRv.setVisibility(View.GONE);
        } else {
            binding.setsLl.setVisibility(View.GONE);
            binding.setsRv.setVisibility(View.VISIBLE);
        }
    }
}