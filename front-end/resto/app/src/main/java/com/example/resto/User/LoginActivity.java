package com.example.resto.User;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.resto.MainActivity;
import com.example.resto.R;
import com.example.resto.Restaurant.AddRestaurantActivity;
import com.example.resto.RestaurantApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private String login, password;
    private boolean isLog = false;
    private FloatingActionButton homeButton;
    private Retrofit retrofit;
    private static RestaurantApi restaurantApi;
    private final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button registerbutton, logbutton;
        final EditText login_input, password_input;

        login_input = findViewById(R.id.login_input);
        password_input = findViewById(R.id.pswd_input);

        confRetrofit();



        homeButton = findViewById(R.id.home_fab);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        logbutton = findViewById(R.id.Logbutton);
        logbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = login_input.getText().toString();
                password = password_input.getText().toString();
                Login();

            }
        });

        registerbutton = findViewById(R.id.registerButton);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity(RegisterActivity.class);
            }
        });
    }

    private void StartActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected void confRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        restaurantApi = retrofit.create(RestaurantApi.class);
    }

    private void Login() {
        restaurantApi.postLogin(login, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse" + response.body());
                System.out.println(response.code() + " " + response.message());
                if (response.message().equals("OK")) {
                    isLog = true;
                    if (isLog) {
                        Toast.makeText(LoginActivity.this, "You are logged in !", Toast.LENGTH_LONG).show();
                        StartActivity(MainActivity.class);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }
                System.out.println(isLog);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
