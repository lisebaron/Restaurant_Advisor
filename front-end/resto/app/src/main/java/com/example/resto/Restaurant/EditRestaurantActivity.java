package com.example.resto.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resto.MainActivity;
import com.example.resto.Menu.Menu;
import com.example.resto.R;
import com.example.resto.RestaurantApi;
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

public class EditRestaurantActivity extends AppCompatActivity {
    private List<Restaurant> restaurants = new ArrayList<>();
    private Restaurant restaurant = new Restaurant();
    private int restaurant_id;
    private float grade;
    private String name, description, localization, phone_number, website, hours;
    private Retrofit retrofit;
    private static RestaurantApi restaurantApi;
    private final String TAG = "RestaurantActivity";
    private EditText restaurantName, restaurantDescription, restaurantGrade, restaurantLocalization, restaurantPhone_number, restaurantWebsite, restaurantHours;
    private Button editRestaurant;

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
        editRestaurant = findViewById(R.id.addRestaurantButton);
        editRestaurant.setText("editRestaurant");

        confRetrofit();

        restaurant_id = getIntent().getIntExtra("restaurant", 0);

        GetRestaurant();

        editRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = restaurantName.getText().toString().trim();
                description = restaurantDescription.getText().toString().trim();
                grade = Float.parseFloat(restaurantGrade.getText().toString().trim());
                localization = restaurantLocalization.getText().toString().trim();
                phone_number = restaurantPhone_number.getText().toString().trim();
                website = restaurantWebsite.getText().toString().trim();
                hours = restaurantHours.getText().toString().trim();

                editRestaurant();
            }
        });
    }

    private void GetRestaurant() {
        restaurantApi.getRestaurants().enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d(TAG, "onResponse");
                if (response.body() != null) {
                    restaurants.addAll(response.body());
                    Log.d(TAG, "oui oui " + restaurant_id);
                    for (Restaurant restaurant1: response.body()) {
                        if (restaurant_id == restaurant1.getId())
                            restaurant = restaurant1;
                    }
                    restaurantName.setText(restaurant.getName());
                    restaurantDescription.setText(restaurant.getDescription());
                    restaurantGrade.setText(Float.toString(restaurant.getGrade()));
                    restaurantLocalization.setText(restaurant.getLocalization());
                    restaurantPhone_number.setText(restaurant.getPhone_number());
                    restaurantWebsite.setText(restaurant.getWebsite());
                    restaurantHours.setText(restaurant.getHours());
                } else {
                    Log.d(TAG, "onResponse : A problem occured!" + response.body().toString());
                    Toast.makeText(EditRestaurantActivity.this, "A problem occured", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
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

    private void editRestaurant() {
        restaurantApi.editRestaurant(getIntent().getIntExtra("id", 0), name, description, grade, localization, phone_number, website, hours).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse" + response.message() + " " + response.code());
                Toast.makeText(EditRestaurantActivity.this, "Restaurant added successfully!", Toast.LENGTH_SHORT).show();
                startActivity( new Intent(EditRestaurantActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
