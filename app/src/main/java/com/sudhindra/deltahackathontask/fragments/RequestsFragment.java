package com.sudhindra.deltahackathontask.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.sudhindra.deltahackathontask.activities.NewRequestActivity;
import com.sudhindra.deltahackathontask.adapters.ResponseAdapter;
import com.sudhindra.deltahackathontask.databinding.FragmentRequestsBinding;
import com.sudhindra.deltahackathontask.models.RequestInfo;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class RequestsFragment extends Fragment {

    private static final int NEW_HTTP_REQUEST = 2;
    private FragmentRequestsBinding binding;

    private ArrayList<ResponseFragment> responseFragments;
    private ResponseAdapter responseAdapter;

    private RequestsFragmentListener listener;

    public interface RequestsFragmentListener {
        void onNewRequest(RequestInfo info);
    }

    public RequestsFragment(RequestsFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestsBinding.inflate(inflater, container, false);

        binding.newRequestBt.setOnClickListener(view -> showNewRequestScreen());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buildViewPager();
    }

    private void buildViewPager() {
        responseFragments = new ArrayList<>();

        responseAdapter = new ResponseAdapter(requireFragmentManager(), getLifecycle());
        responseAdapter.setFragments(responseFragments);

        binding.responseViewPager.setAdapter(responseAdapter);

        TabLayoutMediator mediator = new TabLayoutMediator(binding.responseTabLayout, binding.responseViewPager, (tab, position) -> {
            tab.setText("Request " + (position + 1));
        });

        mediator.attach();
    }

    private void showNewRequestScreen() {
        Intent intent = new Intent(requireContext(), NewRequestActivity.class);
        startActivityForResult(intent, NEW_HTTP_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_HTTP_REQUEST && resultCode == RESULT_OK) {
            RequestInfo info = new Gson().fromJson(data.getStringExtra("requestInfo"), RequestInfo.class);
            listener.onNewRequest(info);
            if (binding.noRequestsTv.getVisibility() == View.VISIBLE) {
                binding.noRequestsTv.setVisibility(View.GONE);
                binding.responseContainer.setVisibility(View.VISIBLE);
            }
            responseFragments.add(new ResponseFragment(info));
            responseAdapter.notifyItemInserted(responseFragments.size() - 1);
            binding.responseViewPager.setCurrentItem(responseFragments.size() - 1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}