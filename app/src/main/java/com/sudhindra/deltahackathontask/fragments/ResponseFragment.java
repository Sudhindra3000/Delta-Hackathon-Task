package com.sudhindra.deltahackathontask.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sudhindra.deltahackathontask.databinding.FragmentResponseBinding;
import com.sudhindra.deltahackathontask.models.RequestInfo;

public class ResponseFragment extends Fragment {

    private FragmentResponseBinding binding;

    private RequestInfo requestInfo;

    public ResponseFragment(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResponseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setInfo(requestInfo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}