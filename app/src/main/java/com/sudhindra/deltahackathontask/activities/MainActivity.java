package com.sudhindra.deltahackathontask.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.sudhindra.deltahackathontask.R;
import com.sudhindra.deltahackathontask.adapters.HomeAdapter;
import com.sudhindra.deltahackathontask.databinding.ActivityMainBinding;
import com.sudhindra.deltahackathontask.fragments.HistoryFragment;
import com.sudhindra.deltahackathontask.fragments.RequestsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buildViewPager();
    }

    private void buildViewPager() {
        HistoryFragment historyFragment = new HistoryFragment();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new RequestsFragment(historyFragment::newRequest));
        fragments.add(historyFragment);

        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.setFragments(fragments);

        binding.viewPager.setAdapter(adapter);

        binding.viewPager.setUserInputEnabled(false);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.requests:
                    binding.viewPager.setCurrentItem(0, false);
                    break;
                case R.id.history:
                    binding.viewPager.setCurrentItem(1, false);
                    break;
            }
            return true;
        });

        binding.bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            if (item.getItemId() == R.id.history) {
                historyFragment.onReselected();
            }
        });
    }
}