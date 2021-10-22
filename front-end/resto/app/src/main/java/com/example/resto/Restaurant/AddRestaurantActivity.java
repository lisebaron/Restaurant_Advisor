package com.example.resto.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resto.MainActivity;
import com.example.resto.Menu.MenuActivity;
import com.example.resto.R;
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

public class AddRestaurantActivity extends AppCompatActivity {
    private EditText restaurantName, restaurantDescription, restaurantGrade, restaurantLocalization, restaurantPhone_number, restaurantWebsite, restaurantHours;
    private Button addRestaurant;
    private FloatingActionButton homeButton;

    private Retrofit retrofit;
    private static RestaurantApi restaurantApi;
    private final String TAG = "Restaurant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        restaurantName = findViewById(R.id.nameInput);
        restaurantDescription = findViewById(R.id.descriptionInput);
        restaurantGrade = findViewById(R.id.gradeInput);
        restaurantLocalization = findViewById(R.id.localizationInput);
        restaurantPhone_number = findViewById(R.id.phone_numberInput);
        restaurantWebsite = findViewById(R.id.websiteInput);
        restaurantHours = findViewById(R.id.hoursInput);
        addRestaurant = findViewById(R.id.addRestaurantButton);

        confRetrofit();

        homeButton = findViewById(R.id.home_fab);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddRestaurantActivity.this, MainActivity.class));
            }
        });

        addRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(restaurantName.getText().toString().trim());
                restaurant.setDescription(restaurantDescription.getText().toString().trim());
                restaurant.setGrade(Float.parseFloat(restaurantGrade.getText().toString().trim()));
                restaurant.setLocalization(restaurantLocalization.getText().toString().trim());
                restaurant.setPhone_number(restaurantPhone_number.getText().toString().trim());
                restaurant.setWebsite(restaurantWebsite.getText().toString().trim());
                restaurant.setHours(restaurantHours.getText().toString().trim());

                addRestaurant(restaurant);
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

    private void addRestaurant(Restaurant restaurant) {
        restaurantApi.addRestaurant(restaurant.getName(), restaurant.getDescription(), restaurant.getGrade(), restaurant.getLocalization(), restaurant.getPhone_number(), restaurant.getWebsite(), restaurant.getHours()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse" + response.body());
                Toast.makeText(AddRestaurantActivity.this, "Restaurant added successfully!", Toast.LENGTH_SHORT).show();
                startActivity( new Intent(AddRestaurantActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
