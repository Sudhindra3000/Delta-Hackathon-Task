package com.sudhindra.deltahackathontask.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.sudhindra.deltahackathontask.R;
import com.sudhindra.deltahackathontask.constants.RequestType;
import com.sudhindra.deltahackathontask.databinding.ActivityNewRequestBinding;
import com.sudhindra.deltahackathontask.models.RequestInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewRequestActivity extends AppCompatActivity {

    private static final String TAG = "NewRequestActivity";
    private ActivityNewRequestBinding binding;

    private OkHttpClient client;
    private RequestType requestType = RequestType.GET;
    private String url;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.newRequestToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        client = new OkHttpClient();

        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        requestType = RequestType.GET;
                        break;
                    case 1:
                        requestType = RequestType.DELETE;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progressDialog = new ProgressDialog(this, R.style.AppTheme_ProgressDialogTheme);
        progressDialog.setMessage("Sending Request");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void sendRequest(View view) {
        url = binding.urlEt.getText().toString();
        if (!url.trim().isEmpty()) {
            progressDialog.show();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            switch (requestType) {
                case DELETE:
                    request = new Request.Builder()
                            .url(url)
                            .delete()
                            .build();
                    break;
            }
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    progressDialog.dismiss();
                    Log.i(TAG, "onFailure: " + e.getMessage());
                    Toast.makeText(NewRequestActivity.this, "Failed to Send Request", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    onBackPressed();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        NewRequestActivity.this.runOnUiThread(() -> {
                            RequestInfo requestInfo = new RequestInfo(url, body, response.code());
                            Intent intent = new Intent();
                            intent.putExtra("requestInfo", new Gson().toJson(requestInfo));
                            setResult(RESULT_OK, intent);
                            onBackPressed();
                        });
                    } else {
                        Log.i(TAG, "onResponse: " + response.message());
                        Toast.makeText(NewRequestActivity.this, "Failed to get Response", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_CANCELED);
                        onBackPressed();
                    }
                }
            });
        } else
            Toast.makeText(this, "Enter Url", Toast.LENGTH_SHORT).show();
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