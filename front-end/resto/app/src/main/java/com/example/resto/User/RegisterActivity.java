package com.example.resto.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.resto.MainActivity;
import com.example.resto.R;
import com.example.resto.RestaurantApi;
import com.example.resto.User.LoginActivity;
import com.example.resto.User.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText login, password, email, firstname, name, age;
    private Button loginbutton, registerbutton;
    private FloatingActionButton homeButton;
    private Retrofit retrofit;
    private static RestaurantApi restaurantApi;
    private User user;
    private final String TAG = "Register";
    private List<User> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerbutton = findViewById(R.id.registerButton);
        login = findViewById(R.id.login_input);
        password = findViewById(R.id.pswd_input);
        email = findViewById(R.id.email_input);
        name = findViewById(R.id.name_input);
        firstname = findViewById(R.id.firstname_input);
        age = findViewById(R.id.age_input);

        loginbutton = findViewById(R.id.LoginButton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity(LoginActivity.class);
            }
        });

        homeButton = findViewById(R.id.home_fab);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        confRetrofit();

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setLogin(login.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                user.setEmail(email.getText().toString().trim());
                user.setName(name.getText().toString().trim());
                user.setFirstname(firstname.getText().toString().trim());
                user.setAge(Integer.parseInt(age.getText().toString().trim()));

                addUser(user);

                StartActivity(LoginActivity.class);
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

    private void addUser(User user) {
        restaurantApi.Register(user.getLogin(), user.getPassword(), user.getEmail(), user.getFirstname(), user.getName(), user.getAge()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse" + response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
