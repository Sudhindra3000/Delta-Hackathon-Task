package com.sudhindra.deltahackathontask.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sudhindra.deltahackathontask.R;
import com.sudhindra.deltahackathontask.databinding.ActivityDetailsBinding;
import com.sudhindra.deltahackathontask.fragments.ResponseFragment;
import com.sudhindra.deltahackathontask.models.RequestInfo;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RequestInfo requestInfo = new Gson().fromJson(getIntent().getStringExtra("item"), RequestInfo.class);

        ResponseFragment responseFragment = new ResponseFragment(requestInfo);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, responseFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}