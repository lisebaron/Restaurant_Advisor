package com.example.resto;


import com.example.resto.Menu.Menu;
import com.example.resto.Restaurant.Restaurant;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantApi {
    @GET("restaurants")
    Call<List<Restaurant>> getRestaurants();

    @GET("restaurant/{id}/menus")
    Call<List<Menu>> getMenus(@Path("id") int id);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> Register(
            @Field("login") String login,
            @Field("password") String password,
            @Field("email") String email,
            @Field("firstname") String firstname,
            @Field("name") String name,
            @Field("age") Integer age
    );

    @FormUrlEncoded
    @POST("auth")
    Call<ResponseBody> postLogin(
            @Field("login") String login,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("restaurant")
    Call<ResponseBody> addRestaurant(
            @Field("name") String name,
            @Field("description") String description,
            @Field("grade") float grade,
            @Field("localization") String localization,
            @Field("phone_number") String phone_number,
            @Field("website") String website,
            @Field("hours") String hours
    );

    @DELETE("restaurant/{id}")
    Call<ResponseBody> delRestaurant(@Path("id") int id);

    @DELETE("/api/restaurant/{id}/menu/{menu_id}")
    Call<ResponseBody> delMenu(
            @Path("id") int id,
            @Path("menu_id") int menu_id
            );

    @FormUrlEncoded
    @POST("restaurant/{id}/menu")
    Call<ResponseBody> addMenu(
            @Path("id") int id,
            @Field("name") String name,
            @Field("description") String description,
            @Field("price") float price,
            @Field("restaurant_id") int restaurant_id
    );

    @FormUrlEncoded
    @PUT("restaurant/{id}")
    Call<ResponseBody> editRestaurant(
            @Path("id") int id,
            @Field("name") String name,
            @Field("description") String description,
            @Field("grade") float grade,
            @Field("localization") String localization,
            @Field("phone_number") String phone_number,
            @Field("website") String website,
            @Field("hours") String hours
    );
}
