package com.sudhindra.deltahackathontask.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sudhindra.deltahackathontask.fragments.ResponseFragment;

import java.util.ArrayList;

public class ResponseAdapter extends FragmentStateAdapter {

    private ArrayList<ResponseFragment> fragments;

    public void setFragments(ArrayList<ResponseFragment> fragments) {
        this.fragments = fragments;
    }

    public ResponseAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
