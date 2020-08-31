package com.sudhindra.deltahackathontask.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sudhindra.deltahackathontask.R;
import com.sudhindra.deltahackathontask.constants.RequestType;
import com.sudhindra.deltahackathontask.databinding.ActivityNewRequestBinding;
import com.sudhindra.deltahackathontask.models.RequestInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewRequestActivity extends AppCompatActivity {

    private static final String TAG = "NewRequestActivity";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private ActivityNewRequestBinding binding;

    private OkHttpClient client;
    private RequestType requestType = RequestType.GET;
    private String url, requestBody;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    private ArrayList<RequestInfo> infos;
    private Type type = new TypeToken<ArrayList<RequestInfo>>() {
    }.getType();

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
                binding.bodyEt.setVisibility(View.VISIBLE);
                switch (i) {
                    case 0:
                        requestType = RequestType.GET;
                        binding.bodyEt.setVisibility(View.GONE);
                        break;
                    case 1:
                        requestType = RequestType.POST;
                        break;
                    case 2:
                        requestType = RequestType.PUT;
                        break;
                    case 3:
                        requestType = RequestType.PATCH;
                        break;
                    case 4:
                        requestType = RequestType.DELETE;
                        break;
                    case 5:
                        requestType = RequestType.HEAD;
                        binding.bodyEt.setVisibility(View.GONE);
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

        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String history = sharedPreferences.getString("history", "");

        if (!history.equals(""))
            infos = gson.fromJson(history, type);
        else
            infos = new ArrayList<>();
    }

    public void sendRequest(View view) {
        url = binding.urlEt.getText().toString();
        if (!url.trim().isEmpty()) {
            progressDialog.show();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            requestBody = binding.bodyEt.getText().toString();
            switch (requestType) {
                case POST:
                    request = new Request.Builder()
                            .url(url)
                            .post(RequestBody.create(requestBody, JSON))
                            .build();
                    break;
                case PUT:
                    request = new Request.Builder()
                            .url(url)
                            .put(RequestBody.create(requestBody, JSON))
                            .build();
                    break;
                case PATCH:
                    request = new Request.Builder()
                            .url(url)
                            .patch(RequestBody.create(requestBody, JSON))
                            .build();
                    break;
                case DELETE:
                    if (!requestBody.trim().isEmpty())
                        request = new Request.Builder()
                                .url(url)
                                .delete(RequestBody.create(requestBody, JSON))
                                .build();
                    else request = new Request.Builder()
                            .url(url)
                            .delete()
                            .build();
                    break;
                case HEAD:
                    request = new Request.Builder()
                            .url(url)
                            .head()
                            .build();
                    break;
            }
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    progressDialog.dismiss();
                    Log.i(TAG, "onFailure: " + e.getMessage());
                    NewRequestActivity.this.runOnUiThread(() -> {
                        Toast.makeText(NewRequestActivity.this, "Failed to sent Request", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        NewRequestActivity.this.runOnUiThread(() -> {
                            RequestInfo requestInfo = new RequestInfo(requestType, url, requestBody, responseBody, response.code());
                            requestInfo.setResponseBody(RequestInfo.prettifyJson(requestInfo.getResponseBody()));
                            infos.add(0, requestInfo);
                            String history = gson.toJson(infos);
                            sharedPreferences.edit().putString("history", history).apply();
                            Intent intent = new Intent();
                            intent.putExtra("requestInfo", new Gson().toJson(requestInfo));
                            setResult(RESULT_OK, intent);
                            onBackPressed();
                        });
                    } else {
                        Log.i(TAG, "onResponse: " + response.message());
                        NewRequestActivity.this.runOnUiThread(() -> {
                            Toast.makeText(NewRequestActivity.this, "Failed to get Response", Toast.LENGTH_SHORT).show();
                        });
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