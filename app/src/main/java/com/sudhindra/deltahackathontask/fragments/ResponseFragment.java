package com.sudhindra.deltahackathontask.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.sudhindra.deltahackathontask.R;
import com.sudhindra.deltahackathontask.databinding.FragmentResponseBinding;
import com.sudhindra.deltahackathontask.models.RequestInfo;

public class ResponseFragment extends Fragment {

    private FragmentResponseBinding binding;

    private RequestInfo requestInfo;
    private ClipboardManager clipboardManager;

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

        clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);

        binding.responseBody.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.response_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.copy:
                        ClipData clip = ClipData.newPlainText("Copied Text", binding.responseBody.getText());
                        clipboardManager.setPrimaryClip(clip);
                        Toast.makeText(requireContext(), "Text Copied", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.export:

                        return true;
                }
                return false;
            });
            popupMenu.show();
            return true;
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}