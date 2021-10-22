package com.example.resto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.example.resto.Restaurant.AddRestaurantActivity;
import com.example.resto.Restaurant.Restaurant;
import com.example.resto.Restaurant.RestaurantActivity;
import com.example.resto.Restaurant.listViewRestoAdapter;
import com.example.resto.User.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static RestaurantApi restaurantApi;
    private List<Restaurant> restaurants = new ArrayList<>();
    private final String TAG = "MainActivity";
    private Button loginbutton, addrestobutton;
    private EditText editText;
    private ListView listView;
    private com.example.resto.Restaurant.listViewRestoAdapter listViewRestoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        listView = findViewById(R.id.listView);
        listViewRestoAdapter = new listViewRestoAdapter(getApplicationContext(), restaurants);
        listView.setAdapter(listViewRestoAdapter);

        confRetrofit();
        getRestaurantsApi();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant1 = restaurants.get(position);
                StartRestaurantActivity(restaurant1.getId()/*, restaurant1.getName(), restaurant1.getDescription(), restaurant1.getGrade(), restaurant1.getLocalization(), restaurant1.getPhone_number(), restaurant1.getWebsite(), restaurant1.getHours()*/);
            }
        });

        addrestobutton = findViewById(R.id.add_resto);
        addrestobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddRestaurantActivity.class));
            }
        });
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

    public void StartRestaurantActivity(int restaurantId/*, String restaurantName, String restaurantDescription, float restaurantGrade, String restaurantLocalization, String restaurantPhone_number, String restaurantWebsite, String restaurantHours*/ ) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("id", restaurantId);

        startActivity(intent);
    }

    private void getRestaurantsApi() {
        restaurantApi.getRestaurants().enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d(TAG, "onResponse");
                if (response.body() != null) {
                    restaurants.addAll(response.body());
                    listViewRestoAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "onResponse : restaurant is empty" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
