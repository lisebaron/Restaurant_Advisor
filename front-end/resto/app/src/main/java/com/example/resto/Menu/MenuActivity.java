package com.example.resto.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resto.MainActivity;
import com.example.resto.R;
import com.example.resto.Restaurant.RestaurantActivity;
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

public class MenuActivity extends AppCompatActivity {
    private int restaurant_id, menu_id;
    private MainActivity mainActivity;
    private FloatingActionButton homeButton;
    private Retrofit retrofit;
    private RestaurantApi restaurantApi;
    private final String TAG = "MenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String name, description, price;
        TextView textName, textDescription, textPrice;
        Button deleteButton;

        restaurant_id = getIntent().getIntExtra("restaurant_id", 0);
        menu_id = getIntent().getIntExtra("menu_id", 0);
        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        price = getIntent().getStringExtra("price");

        textName = findViewById(R.id.menu_name);
        textName.setText(name);
        textDescription = findViewById(R.id.menu_description);
        textDescription.setText(description);
        textPrice = findViewById(R.id.menu_price);
        textPrice.setText(price + " $");

        confRetrofit();

        homeButton = findViewById(R.id.home_fab);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });

        deleteButton = findViewById(R.id.delbutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteMenu();
            }
        });
    }

    public void StartRestaurantActivity(int restaurantId) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra("id", restaurantId);

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

    private void DeleteMenu() {
        restaurantApi.delMenu(restaurant_id, menu_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse");
                StartRestaurantActivity(restaurant_id);
                Toast.makeText(MenuActivity.this, "Menu deleted successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
