package com.example.resto.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resto.Menu.AddMenuActivity;
import com.example.resto.MainActivity;
import com.example.resto.Menu.Menu;
import com.example.resto.Menu.MenuActivity;
import com.example.resto.Menu.listViewMenuAdapter;
import com.example.resto.R;
import com.example.resto.RestaurantApi;
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

public class RestaurantActivity extends AppCompatActivity {
    private List<Menu> menus = new ArrayList<>();
    private List<Restaurant> restaurants;
    private Restaurant restaurant;
    private com.example.resto.Menu.listViewMenuAdapter listViewMenuAdapter;
    private ListView listView;
    private Button deleteButton, addMenu, editButton;
    private FloatingActionButton homeButton;
    private int restaurant_id;
    private float grade;
    private String name, description, localization, phone_number, website, hours;
    private Retrofit retrofit;
    private static RestaurantApi restaurantApi;
    private final String TAG = "RestaurantActivity";
    private TextView textname, textdescription, textgrade, textlocalization, textphone_number, textwebsite, texthours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //region Extras & TextViews
        restaurant_id = getIntent().getIntExtra("id", 0);
        restaurants = new ArrayList<>();

        confRetrofit();
        GetRestaurant();

        textname = findViewById(R.id.restaurant_name);
        textdescription = findViewById(R.id.restaurant_description);
        textgrade = findViewById(R.id.restaurant_grade);
        textlocalization = findViewById(R.id.restaurant_localization);
        textphone_number = findViewById(R.id.restaurant_phone_number);
        textwebsite = findViewById(R.id.restaurant_website);
        texthours = findViewById(R.id.restaurant_hours);
        //endregion

        listView = findViewById(R.id.listView);
        listViewMenuAdapter = new listViewMenuAdapter(getApplicationContext(), menus);
        listView.setAdapter(listViewMenuAdapter);

        getMenuApi();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Menu menu = menus.get(position);
                intentMenuActivity( restaurant_id, menu.getId(), menu.getName(), menu.getDescription(), menu.getPrice());
            }
        });

        homeButton = findViewById(R.id.home_fab);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestaurantActivity.this, MainActivity.class));
            }
        });

        deleteButton = findViewById(R.id.delbutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteResto();
            }
        });

        addMenu = findViewById(R.id.add_menu);
        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, AddMenuActivity.class);
                intent.putExtra("restaurant_id", restaurant_id);
                startActivity(intent);
            }
        });

        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, EditRestaurantActivity.class);
                intent.putExtra("restaurant", restaurant_id);
                startActivity(intent);
            }
        });
    }

    private void intentMenuActivity(int restaurantId, int menuId, String menuName, String menuDescription, float menuPrice) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("restaurant_id", restaurantId);
        intent.putExtra("menu_id", menuId);
        intent.putExtra("name", menuName);
        intent.putExtra("description", menuDescription);
        intent.putExtra("price", Float.toString(menuPrice));

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

                    name = restaurant.getName();
                    description = restaurant.getDescription();
                    grade = restaurant.getGrade();
                    localization = restaurant.getLocalization();
                    phone_number = restaurant.getPhone_number();
                    website = restaurant.getWebsite();
                    hours = restaurant.getHours();

                    textname.setText(name);
                    textdescription.setText(description);
                    textgrade.setText(Float.toString(grade));
                    textlocalization.setText(localization);
                    textphone_number.setText(phone_number);
                    textwebsite.setText(website);
                    texthours.setText(hours);
                } else {
                    Log.d(TAG, "onResponse : A problem occured!" + response.body().toString());
                    Toast.makeText(RestaurantActivity.this, "A problem occured", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void DeleteResto() {
        restaurantApi.delRestaurant(restaurant_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse");
                startActivity(new Intent(RestaurantActivity.this, MainActivity.class));
                Toast.makeText(RestaurantActivity.this, "Restaurant deleted successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getMenuApi() {
        restaurantApi.getMenus(restaurant_id).enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                Log.d(TAG, "onResponse");

                if (response.body() != null) {
                    menus.addAll(response.body());
                    listViewMenuAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "onResponse : menu is empty" + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
