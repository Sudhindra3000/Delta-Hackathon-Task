package com.sudhindra.deltahackathontask.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sudhindra.deltahackathontask.activities.DetailsActivity;
import com.sudhindra.deltahackathontask.adapters.HistoryAdapter;
import com.sudhindra.deltahackathontask.databinding.FragmentHistoryBinding;
import com.sudhindra.deltahackathontask.models.RequestInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    private Gson gson = new Gson();

    public ArrayList<RequestInfo> infos;
    private HistoryAdapter adapter;
    private Type type = new TypeToken<ArrayList<RequestInfo>>() {
    }.getType();

    public void newRequest(RequestInfo info) {
        if (infos != null) {
            infos.add(0, info);
            adapter.notifyItemInserted(0);
        }
    }

    public void onReselected() {
        binding.historyRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String history = sharedPreferences.getString("history", "");

        if (!history.equals(""))
            infos = gson.fromJson(history, type);
        else
            infos = new ArrayList<>();

        buildRecyclerView();
    }

    private void buildRecyclerView() {
        adapter = new HistoryAdapter(infos);

        binding.historyRecyclerView.setHasFixedSize(true);
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historyRecyclerView.setAdapter(adapter);

        adapter.setListener(pos -> {
            Intent intent = new Intent(requireContext(), DetailsActivity.class);
            intent.putExtra("item", gson.toJson(infos.get(pos)));
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}