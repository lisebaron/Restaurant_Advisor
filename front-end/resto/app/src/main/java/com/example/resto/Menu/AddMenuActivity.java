package com.example.resto.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resto.MainActivity;
import com.example.resto.Menu.Menu;
import com.example.resto.R;
import com.example.resto.Restaurant.AddRestaurantActivity;
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

public class AddMenuActivity extends AppCompatActivity {
    private EditText menuName, menuDescription, menuPrice;
    private Button addMenu;
    private FloatingActionButton homeButton;
    private int menu_id, restaurant_id;
    private Retrofit retrofit;
    private static RestaurantApi restaurantApi;
    private final String TAG = "Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        menu_id = getIntent().getIntExtra("id", 0);
        restaurant_id = getIntent().getIntExtra("restaurant_id", 0);
        menuName = findViewById(R.id.menuNameInput);
        menuDescription = findViewById(R.id.menuDescriptionInput);
        menuPrice = findViewById(R.id.menuPriceInput);

        confRetrofit();

        homeButton = findViewById(R.id.home_fab);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMenuActivity.this, MainActivity.class));
            }
        });

        addMenu = findViewById(R.id.addMenuButton);
        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = new Menu();
                menu.setName(menuName.getText().toString().trim());
                menu.setDescription(menuDescription.getText().toString().trim());
                menu.setPrice(Float.parseFloat(menuPrice.getText().toString().trim()));

                addMenu(menu);
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

    private void addMenu(Menu menu) {
        restaurantApi.addMenu(menu_id, menu.getName(), menu.getDescription(), menu.getPrice(), restaurant_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse" + response.message() + " " + response.code());
                Toast.makeText(AddMenuActivity.this, "Menu Successfully added!", Toast.LENGTH_SHORT).show();
                StartRestaurantActivity(restaurant_id);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
